package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.DTO.AadhaarDetails;
import com.example.aurigraph.farmers.DTO.AadhaarDetailsDTO;
import com.example.aurigraph.farmers.DTO.IssuedDocumentDTO;

import com.example.aurigraph.farmers.Service.ApiSetuService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import org.w3c.dom.*;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ApiSetuServiceImpl implements ApiSetuService {

    private final WebClient webClient;

    @Value("${digiLocker.client.id}")
    private String digiLockerClientId;

    @Value("${digiLocker.client.secret}")
    private String digiLockerClientSecret;

    @Value("${digiLocker.redirect.uri}")
    private String digiLockerRedirectUrl;

    @Value("${digiLocker.token.url}")
    private String tokenUrl;

    @Value("${digiLocker.docs.url.issued}")
    private String issuedDocsUrl;

    @Value("${digiLocker.docs.url.e-aadhaar}")
    private String getEAdharDocsUrl;

    @Value("${digiLocker.docs.by.uri}")
    private String getDocsByUri;


    private final StringRedisTemplate redisTemplate;

    private final RestTemplate restTemplate = new RestTemplate();


    public ApiSetuServiceImpl(WebClient.Builder webClientBuilder, StringRedisTemplate redisTemplate) {
        this.webClient = webClientBuilder.build();
        this.redisTemplate = redisTemplate;
    }



    @Override
    public void saveAccessToken(String mobile, String accessToken, String refreshToken, long expiresIn) {
        if (accessToken != null && !accessToken.isEmpty()) {
            redisTemplate.opsForValue().set("access_token:+" +mobile, accessToken, expiresIn, TimeUnit.SECONDS);
        }

        if(refreshToken != null) {
            redisTemplate.opsForValue().set("refresh_token:+" +mobile, refreshToken, 7, TimeUnit.DAYS);
        }

    }

    @Override
    public String getAccessToken(String mobile) {
        return redisTemplate.opsForValue().get("access_token:" + mobile);
    }

    @Override
    public boolean clearTokens(String mobile) {
       boolean clearAccess = clearAccessToken(mobile);
       boolean clearRefresh = clearRefreshToken(mobile);
       return clearAccess || clearRefresh;

    }

    public boolean clearAccessToken(String mobile) {
        return redisTemplate.delete("access_token:" +mobile);
    }
    public boolean clearRefreshToken(String mobile) {
        return redisTemplate.delete("access_token:" +mobile);
    }

    @Override
    public String getRefreshToken(String mobile) {
        return redisTemplate.opsForValue().get("refresh_token:" + mobile);
    }

    @Override
    public Mono<Map<String, Object>> getAccessToken(String code, String codeVerifier) {
        return webClient.post()
                .uri(tokenUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(Objects.requireNonNull(UriComponentsBuilder.newInstance()
                        .queryParam("code", code)
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", digiLockerClientId)
                        .queryParam("client_secret", digiLockerClientSecret)
                        .queryParam("redirect_uri", digiLockerRedirectUrl)
                        .queryParam("code_verifier", codeVerifier)
                        .build()
                        .getQuery()))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            System.err.println("Error in getAccessToken: " + errorBody);
                            Map<String, Object> errorResponse = new HashMap<>();
                            errorResponse.put("error", "Token Request Failed");
                            errorResponse.put("message", errorBody);
                            return Mono.error(new RuntimeException(errorBody)); // Pass error message
                        }))
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(response -> response)
                .doOnError(error -> {
                    System.err.println("Exception in getAccessToken: " + error.getMessage());
                })
                .onErrorResume(error -> {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("error", "Internal Server Error");
                    errorResponse.put("message", error.getMessage()); // Send exact error message
                    return Mono.just(errorResponse);
                });
    }

    public Mono<Map<String, Object>> refreshAccessToken(String refreshToken) {
        return webClient.post()
                .uri(tokenUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(Objects.requireNonNull(UriComponentsBuilder.newInstance()
                        .queryParam("refresh_token", refreshToken)
                        .queryParam("grant_type", "refresh_token")
                        .queryParam("client_id", digiLockerClientId)
                        .queryParam("client_secret", digiLockerClientSecret)
                        .build()
                        .getQuery()))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            System.err.println("Error in refreshAccessToken: " + errorBody);
                            return Mono.error(new RuntimeException("Failed to refresh access token: " + errorBody));
                        }))
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .doOnError(error -> System.err.println("Exception in refreshAccessToken: " + error.getMessage()))
                .onErrorResume(e -> Mono.empty()); // Return empty if an error occurs
    }


    @Override
    public List<IssuedDocumentDTO> getIssuedDocs(String accessToken) {
        Map<String, Object> response = webClient.get()
                .uri(issuedDocsUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block(); // Blocking to get synchronous response

        return mapToIssuedDocumentsDTO(response);
    }


    private List<IssuedDocumentDTO> mapToIssuedDocumentsDTO(Map<String, Object> response) {

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
           List<IssuedDocumentDTO> issuedDocuments = items.stream()
                .map(this::mapToIssuedDocumentDTO)
                .collect(Collectors.toList());


        return issuedDocuments;
    }

    private IssuedDocumentDTO mapToIssuedDocumentDTO(Map<String, Object> item) {
        IssuedDocumentDTO documentDTO = new IssuedDocumentDTO();
        documentDTO.setName((String) item.get("name"));
        documentDTO.setType((String) item.get("type"));
        documentDTO.setSize((String) item.get("size"));
        documentDTO.setDate((String) item.get("date"));
        documentDTO.setParent((String) item.get("parent"));
        documentDTO.setMime((List<String>) item.get("mime"));
        documentDTO.setUri((String) item.get("uri"));
        documentDTO.setDoctype((String) item.get("doctype"));
        documentDTO.setDescription((String) item.get("description"));
        documentDTO.setIssuerId((String) item.get("issuerid"));
        documentDTO.setIssuer((String) item.get("issuer"));

        return documentDTO;
    }

    @Override
    public AadhaarDetailsDTO getDigiLockerAadhaarDocsByUri(String uri, String docType, String mobile) {
//        String accessToken = getAccessToken(mobile);
        String accessToken = "7dbb6a7579970e9f190c3c8dd795b2c2fcda6b36";
            return downloadAndSaveAadhaarAsPdf(accessToken);


    }

    @Override
    public MultipartFile getDigiLockerDocsByUri(String uri, String docType, String mobile) {
//        String accessToken = getAccessToken(mobile);
        String accessToken = "7dbb6a7579970e9f190c3c8dd795b2c2fcda6b36";
       return downloadDocumentAsMultipartFile(uri,accessToken);

    }

    public AadhaarDetailsDTO downloadAndSaveAadhaarAsPdf(String accessToken) {
        String xml = getEAadhaarXml(accessToken);
        AadhaarDetails aadhaarDetails = parseAadhaarXml(xml);
        AadhaarDetailsDTO aadhaarDetailsDTO = new AadhaarDetailsDTO();
        aadhaarDetailsDTO.setCo(aadhaarDetails.getCo());
        aadhaarDetailsDTO.setName(aadhaarDetails.getName());
        aadhaarDetailsDTO.setDist(aadhaarDetails.getDist());
        aadhaarDetailsDTO.setDob(aadhaarDetails.getDob());
        aadhaarDetailsDTO.setGender(aadhaarDetails.getGender());
        aadhaarDetailsDTO.setLoc(aadhaarDetails.getLoc());
        aadhaarDetailsDTO.setPc(aadhaarDetails.getPc());
        aadhaarDetailsDTO.setPo(aadhaarDetails.getPo());
        aadhaarDetailsDTO.setUid(aadhaarDetails.getUid());
        aadhaarDetailsDTO.setState(aadhaarDetails.getState());
        aadhaarDetailsDTO.setVtc(aadhaarDetails.getVtc());
        aadhaarDetailsDTO.setYob(aadhaarDetails.getYob());

        aadhaarDetailsDTO.setImage(PdfGenerator.saveAadhaarAsPdf(aadhaarDetails, "aadhaar_" + aadhaarDetails.name + ".pdf"));
        return aadhaarDetailsDTO;
    }

    public String getEAadhaarXml(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getEAdharDocsUrl,
                HttpMethod.GET,
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch Doc XML: " + response.getStatusCode());
        }
    }


    public MultipartFile downloadDocumentAsMultipartFile(String url, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.ALL)); // Accept any content type

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                getDocsByUri+url,
                HttpMethod.GET,
                entity,
                byte[].class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            byte[] fileBytes = response.getBody();

            String contentType = response.getHeaders().getContentType() != null
                    ? response.getHeaders().getContentType().toString()
                    : "application/octet-stream";

            // Try to extract filename from header
            String fileName = "document";
            List<String> disposition = response.getHeaders().get("Content-Disposition");
            if (disposition != null && !disposition.isEmpty()) {
                String contentDisp = disposition.get(0);
                if (contentDisp.contains("filename=")) {
                    fileName = contentDisp.split("filename=")[1].replace("\"", "").trim();
                }
            } else {
                // Fallback extension
                if (contentType.contains("pdf")) {
                    fileName += ".pdf";
                } else if (contentType.contains("jpeg")) {
                    fileName += ".jpeg";
                } else if (contentType.contains("png")) {
                    fileName += ".png";
                }
            }

            return new MockMultipartFile(fileName, fileName, contentType, fileBytes);
        } else {
            throw new RuntimeException("Failed to download document: " + response.getStatusCode());
        }
    }

    private  String parseName(String xmlContent) {
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xmlContent.getBytes()));

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("PrintLetterBarcodeData");

            if (nodeList.getLength() > 0) {
                Element element = (Element) nodeList.item(0);
                return element.getAttribute("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public AadhaarDetails parseAadhaarXml(String xmlContent) {
        AadhaarDetails details = new AadhaarDetails();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));

            doc.getDocumentElement().normalize();

            if (xmlContent.contains("<PrintLetterBarcodeData")) {
                // ✅ Legacy Aadhaar XML format
                NodeList dataNodes = doc.getElementsByTagName("PrintLetterBarcodeData");
                if (dataNodes.getLength() > 0) {
                    Element dataElement = (Element) dataNodes.item(0);
                    details.name = dataElement.getAttribute("name");
                    details.gender = dataElement.getAttribute("gender");
                    details.dob = dataElement.getAttribute("dob");
                    details.yob = dataElement.getAttribute("yob");
                    details.uid = dataElement.getAttribute("uid");
                    details.co = dataElement.getAttribute("co");
                    details.loc = dataElement.getAttribute("loc");
                    details.po = dataElement.getAttribute("po");
                    details.vtc = dataElement.getAttribute("vtc");
                    details.dist = dataElement.getAttribute("dist");
                    details.state = dataElement.getAttribute("state");
                    details.pc = dataElement.getAttribute("pc");
                } else {
                    System.out.println("⚠️ No <PrintLetterBarcodeData> element found in Aadhaar XML");
                }
            } else if (xmlContent.contains("<KycRes")) {
                // ✅ e-KYC Aadhaar XML format
                NodeList uidDataList = doc.getElementsByTagName("UidData");
                if (uidDataList.getLength() > 0) {
                    Element uidData = (Element) uidDataList.item(0);

                    details.uid = uidData.getAttribute("uid");

                    Element poi = (Element) uidData.getElementsByTagName("Poi").item(0);
                    if (poi != null) {
                        details.name = poi.getAttribute("name");
                        details.dob = poi.getAttribute("dob");
                        details.gender = poi.getAttribute("gender");
                    }

                    Element poa = (Element) uidData.getElementsByTagName("Poa").item(0);
                    if (poa != null) {
                        details.co = poa.getAttribute("co");
                        details.loc = poa.getAttribute("loc");
                        details.po = poa.getAttribute("po");
                        details.vtc = poa.getAttribute("vtc");
                        details.dist = poa.getAttribute("dist");
                        details.state = poa.getAttribute("state");
                        details.pc = poa.getAttribute("pc");
                    }
                } else {
                    System.out.println("⚠️ No <UidData> found in e-KYC Aadhaar XML");
                }
            } else {
                System.out.println("⚠️ Unknown Aadhaar XML format");
            }

            // ✅ Extract base64 photo (common for both formats)
            NodeList photoNodes = doc.getElementsByTagName("Pht");
            if (photoNodes.getLength() > 0) {
                String base64Photo = photoNodes.item(0).getTextContent();
                byte[] photoBytes = Base64.getDecoder().decode(base64Photo);
                details.photo = ImageIO.read(new ByteArrayInputStream(photoBytes));
            } else {
                System.out.println("⚠️ No <Pht> tag (photo) found in Aadhaar XML");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }





}
