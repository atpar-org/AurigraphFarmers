package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.DTO.AadhaarDetailsDTO;
import com.example.aurigraph.farmers.DTO.IssuedDocumentDTO;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ApiSetuService {
    void saveAccessToken(String mobile, String accessToken, String refreshToken, long expiresIn);

    String getAccessToken(String mobile);

    boolean clearTokens(String mobile);

    String getRefreshToken(String mobile);

    Mono<Map<String, Object>> getAccessToken(String code, String codeVerifier);
    Mono<Map<String, Object>>  refreshAccessToken(String refreshToken);

    List<IssuedDocumentDTO> getIssuedDocs(String accessToken);

    AadhaarDetailsDTO getDigiLockerAadhaarDocsByUri(String uri, String docType, String mobile);
    MultipartFile getDigiLockerDocsByUri(String uri, String docType, String mobile);

    void revoke(String mobile);
}
