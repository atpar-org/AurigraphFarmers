package com.example.aurigraph.farmers.Controller;

import com.example.aurigraph.farmers.DTO.AadhaarDetailsDTO;
import com.example.aurigraph.farmers.DTO.IssuedDocumentDTO;

import com.example.aurigraph.farmers.DTO.LandOwnerWithIssuedDocs;
import com.example.aurigraph.farmers.Domain.LandOwner;
import com.example.aurigraph.farmers.Domain.User;
import com.example.aurigraph.farmers.Mapping.LandOwnerMapping;
import com.example.aurigraph.farmers.Repository.LandOwnerRepository;
import com.example.aurigraph.farmers.Response.ResponseVO;
import com.example.aurigraph.farmers.Security.SecurityUtils;
import com.example.aurigraph.farmers.Service.ApiSetuService;
import com.example.aurigraph.farmers.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/apisetu")
public class ApiSetuController {

    @Autowired
    private ApiSetuService apiSetuService;
    @Autowired
    private UserService userService;
    @Autowired
    private LandOwnerRepository landOwnerRepository;

    private LandOwnerMapping landOwnerMapping;

    @GetMapping("/auth-and-getDocs")
    public ResponseVO<IssuedDocumentDTO> getAuthAndDocs(@RequestParam String code,
                                                         @RequestParam(required = false) String state,
                                                         @RequestParam String codeVerifier  ) {

        ResponseVO<IssuedDocumentDTO> responseVO = new ResponseVO<>();

        String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.findByPhoneNumer(userLogin);
        if(user.isEmpty()){
            responseVO.setStatus(400);
            responseVO.setMessage("User not found");
            return responseVO;
        }
        Map<String,Object> accessResponse = apiSetuService.getAccessToken(code, codeVerifier).block();
        if(accessResponse!=null && !accessResponse.isEmpty() && !accessResponse.containsKey("error")){
            String accessToken = (String) accessResponse.get("access_token");
            System.out.println("access_token:"+accessToken);
            String refreshToken = (String) accessResponse.get("refresh_token");
            long expiresIn = ((Number) accessResponse.get("expires_in")).longValue();
            apiSetuService.saveAccessToken(user.get().getPhoneNumber(), accessToken, refreshToken, expiresIn);
//            apiSetuService.revoke(mobile);
           List<IssuedDocumentDTO> issuedDocuments = fetchIssuedDocs(accessToken);
            responseVO.setStatus(200);
            responseVO.setMessage("Success");
            responseVO.setData(issuedDocuments);
            return responseVO;
//            return new ResponseEntity<>(new IssuedDocumentsDTO(), HttpStatus.OK);
        }

        else{
            responseVO.setStatus(401);
            responseVO.setMessage("Invalid credentials or code verifier");
            return responseVO;

        }

    }

    @GetMapping("/auth-and-saveLandOwner")
    public ResponseVO<LandOwnerWithIssuedDocs> getAuthAndSaveLandOwner(@RequestParam String code,
                                                        @RequestParam(required = false) String state,
                                                        @RequestParam String codeVerifier  ) throws IOException {

        ResponseVO<LandOwnerWithIssuedDocs> responseVO = new ResponseVO<>();

        String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.findByPhoneNumer(userLogin);
        if(user.isEmpty()){
            responseVO.setStatus(400);
            responseVO.setMessage("User not found");
            return responseVO;
        }
        LandOwner landOwner = new LandOwner();
        Optional<LandOwner> landOwnerOptional = landOwnerRepository.findByMobile(user.get().getPhoneNumber());
        if(landOwnerOptional.isPresent()){
            landOwner = landOwnerOptional.get();
        }
        else{
            landOwner.setMobile(user.get().getPhoneNumber());
        }
        Map<String,Object> accessResponse = apiSetuService.getAccessToken(code, codeVerifier).block();
        if(accessResponse!=null && !accessResponse.isEmpty() && !accessResponse.containsKey("error")){

            String accessToken = (String) accessResponse.get("access_token");
            System.out.println("access_token:"+accessToken);

            String refreshToken = (String) accessResponse.get("refresh_token");
            long expiresIn = ((Number) accessResponse.get("expires_in")).longValue();

            apiSetuService.saveAccessToken(user.get().getPhoneNumber(), accessToken, refreshToken, expiresIn);

            List<IssuedDocumentDTO> issuedDocuments = fetchIssuedDocs(accessToken);

            for(IssuedDocumentDTO issuedDocumentDTO : issuedDocuments){
                if ("ADHAR".equalsIgnoreCase(issuedDocumentDTO.getDoctype())) {
                    AadhaarDetailsDTO aadhaarDetailsDTO = apiSetuService.getDigiLockerAadhaarDocsByUri(issuedDocumentDTO.getUri(), issuedDocumentDTO.getDoctype(), landOwner.getMobile());
                    if(aadhaarDetailsDTO!=null){
                      landOwner = landOwnerMapping.saveLandOwnerByAadhaarDetails(aadhaarDetailsDTO,issuedDocumentDTO.getDoctype(),landOwner);

                    }
                }
            }

            LandOwnerWithIssuedDocs landOwnerWithIssuedDocs =landOwnerMapping.domainToDTO(landOwner);
            landOwnerWithIssuedDocs.setIssuedDocuments(issuedDocuments);
            responseVO.setStatus(200);
            responseVO.setMessage("Success");
            responseVO.setData(Collections.singletonList(landOwnerWithIssuedDocs));
            return responseVO;
        }

        else{
            responseVO.setStatus(401);
            responseVO.setMessage("Invalid credentials or code verifier");
            return responseVO;

        }

    }


    @GetMapping("/get-issued-docs")
    public ResponseVO<IssuedDocumentDTO> getIssuedDocs() {
        ResponseVO<IssuedDocumentDTO> responseVO = new ResponseVO<>();

        String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.findByPhoneNumer(userLogin);
        if(user.isEmpty()){
            responseVO.setStatus(400);
            responseVO.setMessage("User not found");
            return responseVO;
        }
        String accessToken = apiSetuService.getAccessToken(user.get().getPhoneNumber());
        System.out.println("access_token:"+accessToken);
        if (accessToken == null) {
            String refreshToken = apiSetuService.getRefreshToken(user.get().getPhoneNumber());

            if (refreshToken == null) {
                responseVO.setStatus(401);
                responseVO.setMessage("Refresh token && access token are null");
                return responseVO;

            }
            Map<String, Object> refreshAccessResponse = refreshTokenAndRetry(user.get().getPhoneNumber(), refreshToken).block();
            if (refreshAccessResponse == null) {
                responseVO.setStatus(401);
                responseVO.setMessage("Failed to get response using refresh token");
                return responseVO;
            }
            accessToken = (String) refreshAccessResponse.get("access_token");
        }

        List<IssuedDocumentDTO> issuedDocuments =  fetchIssuedDocs(accessToken);
        if (issuedDocuments == null) {
            responseVO.setStatus(401);
            responseVO.setMessage("Failed to fetch issued docs");
            return responseVO;
        }
        responseVO.setStatus(200);
        responseVO.setMessage("Success");
        responseVO.setData(issuedDocuments);
        return responseVO;
    }

    private List<IssuedDocumentDTO> fetchIssuedDocs(String accessToken) {
        return   apiSetuService.getIssuedDocs(accessToken);

    }

    @PutMapping("/remove-tokens")
    private ResponseVO clearAccessAndRefreshToken() {
        ResponseVO responseVO = new ResponseVO();

        String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.findByPhoneNumer(userLogin);
        if(user.isEmpty()){
            responseVO.setStatus(400);
            responseVO.setMessage("User not found");
            return responseVO;
        }

        boolean clearTokens = apiSetuService.clearTokens(user.get().getPhoneNumber());
        if(clearTokens){
            responseVO.setStatus(200);
            responseVO.setMessage("Success");
            return responseVO;
        }
        responseVO.setStatus(500);
        responseVO.setMessage("Failed to clear access and refresh token");
        return responseVO;
    }



//    private Mono<ResponseEntity<IssuedDocumentsDTO>> handleAccessTokenError(String mobile) {
//        String refreshToken = apiSetuService.getRefreshToken(mobile);
//                if(refreshToken != null) {
//                    refreshTokenAndRetry(mobile, refreshToken)
//                            .flatMap(refreshAccessResponse -> {
//                                if (refreshAccessResponse != null && refreshAccessResponse.containsKey("access_token")) {
//                                    String accessToken = (String) refreshAccessResponse.get("access_token");
//                                    return fetchIssuedDocs(accessToken);
//                                } else {
//                                    return Mono.just(ResponseEntity.status(401).body(null));
//                                }
//                            })
//                            .defaultIfEmpty(ResponseEntity.status(401).body(null));
//                }
//                return Mono.just(ResponseEntity.status(401).body(null));
//
//    }


    private Mono<Map<String, Object>> refreshTokenAndRetry(String mobile,String refreshToken) {


        return apiSetuService.refreshAccessToken(refreshToken)
                .map(response -> {
                    String newAccessToken = (String) response.get("access_token");
                    String newRefreshToken = (String) response.get("refresh_token");
                    long expiresIn = ((Number) response.get("expires_in")).longValue();
                    apiSetuService.saveAccessToken(mobile, newAccessToken, newRefreshToken, expiresIn);
                    return response;
                });
    }

//    @GetMapping("/apisetuauth")
//    public ResponseEntity<String> handleRedirect(@RequestParam(required = false) String code,
//                                                 @RequestParam(required = false) String state,
//                                                 @RequestParam(required = false) String error,
//                                                 @RequestParam(required = false, name = "error_description") String errorDescription) {
//        if (error != null) {
//            System.out.println("Error: " + error);
//            System.out.println("Error Description: " + errorDescription);
//            return ResponseEntity.badRequest().body("Error occurred: " + errorDescription);
//        }
//
//        System.out.println("Authorization Code: " + code);
//        System.out.println("State: " + state);
//
//        return ResponseEntity.ok("Received code: " + code + ", state: " + state);
//    }



}
