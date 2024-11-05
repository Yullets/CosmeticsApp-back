package com.cosmetics.app.service;

import com.cosmetics.app.model.TokensPair;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUserNameFromAccessToken(String token);

    String extractUserNameFromRefreshToken(String token);

    Long extractRefreshID(String token);

    TokensPair generateToken(UserDetails userDetails);

    boolean isAccessTokenValid(String token, UserDetails userDetails);

    boolean isRefreshTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails);

    TokensPair refreshAuthToken(String refreshToken);

    String generateAccessTokenForAdminRegistration();
}
