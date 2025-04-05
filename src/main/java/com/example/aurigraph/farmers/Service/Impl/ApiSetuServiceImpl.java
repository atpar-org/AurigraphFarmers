package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.DTO.IssuedDocumentDTO;

import com.example.aurigraph.farmers.Service.ApiSetuService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

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

    private final StringRedisTemplate redisTemplate;

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


}
