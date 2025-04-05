package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.Domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface JwtService {

    String extractUsername(String token);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaims, User user);

    long getExpirationTime();

    String buildToken(
            Map<String, Object> extraClaims,
            User user,
            long expiration
    );

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);
}