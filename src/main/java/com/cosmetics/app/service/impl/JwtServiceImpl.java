package com.cosmetics.app.service.impl;

import com.cosmetics.app.entity.RefreshToken;
import com.cosmetics.app.entity.User;
import com.cosmetics.app.exception.InvalidRefreshTokenException;
import com.cosmetics.app.exception.RefreshTokenAlreadyUsedException;
import com.cosmetics.app.model.TokensPair;
import com.cosmetics.app.service.JwtService;
import com.cosmetics.app.service.UserService;
import com.cosmetics.app.service.UserTokensService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    public static final String REFRESH_TOKEN_ID = "refreshTokenID";

    @Value("${token.signing.accessKey}")
    private String jwtSigningKey;

    @Value("${token.signing.refreshKey}")
    private String jwtSigningRefreshKey;

    @Value("${token.signing.accessTokenLifetimeSecs}")
    private int accessTokenLifetime;

    @Value("${token.signing.refreshTokenLifetimeDays}")
    private int refreshTokenLifetime;

    private final UserTokensService userTokensService;
    private final UserService userService;

    @Override
    public String extractUserNameFromAccessToken(String token) {
        return extractClaim(token, Claims::getSubject, getSigningKey());
    }

    @Override
    public String extractUserNameFromRefreshToken(String token) {
        return extractClaim(token, Claims::getSubject, getSigningRefreshKey());
    }

    @Override
    public Long extractRefreshID(String token) {
        final Claims claims = extractAllClaims(token, getSigningKey());
        return claims.get(REFRESH_TOKEN_ID, Long.class);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers, Key key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token, Key key) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getSigningRefreshKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningRefreshKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Long saveRefreshToken(UserDetails userDetails, String refreshToken) {
        RefreshToken userTokens = RefreshToken.builder()
                .user((User) userDetails)
                .refreshToken(refreshToken)
                .build();

        return userTokensService.save(userTokens);
    }

    private Map<String, Object> createClaims(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User) {
            User customUserDetails = (User) userDetails;
            claims.put("id", customUserDetails.getId());
            claims.put("login", customUserDetails.getUsername());
        }

        return claims;
    }

    @Override
    public TokensPair generateToken(UserDetails userDetails) {
        Map<String, Object> claims = createClaims(userDetails);
        String refreshToken = generateRefreshToken(claims, userDetails);
        Long id = saveRefreshToken(userDetails, refreshToken);
        claims.put(REFRESH_TOKEN_ID, id);
        String accessToken = generateAccessToken(claims, userDetails);

        return new TokensPair(accessToken, refreshToken);
    }

    private String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(calculateAccessExpirationTime(accessTokenLifetime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isAccessTokenExpired(String token) {
        return extractExpiration(token, getSigningKey()).before(new Date());
    }

    private boolean isRefreshTokenExpired(String token) {
        return extractExpiration(token, getSigningRefreshKey()).before(new Date());
    }

    private Date extractExpiration(String token, Key key) {
        return extractClaim(token, Claims::getExpiration, key);
    }

    @Override
    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserNameFromAccessToken(token);
        return (userName.equals(userDetails.getUsername())) && !isAccessTokenExpired(token);
    }

    @Override
    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        String userName = extractUserNameFromRefreshToken(token);
        return (userName.equals(userDetails.getUsername())) && !isRefreshTokenExpired(token);
    }

    @Override
    public String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(calculateRefreshExpirationTime(refreshTokenLifetime))
                .signWith(getSigningRefreshKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public TokensPair refreshAuthToken(String refreshToken) {
        var username = extractUserNameFromRefreshToken(refreshToken);
        UserDetails userDetails = userService
                .userDetailsService()
                .loadUserByUsername(username);
        if (!isRefreshTokenValid(refreshToken, userDetails)) {
            throw new InvalidRefreshTokenException("Невалидный рефреш токен");
        }
        RefreshToken userRefreshToken;
        try {
            userRefreshToken = userTokensService.getUserTokensByUserAndRefreshToken((User) userDetails, refreshToken);
        } catch (Exception e) {
            throw new RefreshTokenAlreadyUsedException("Рефреш токен был использован");
        }
        userTokensService.delete(userRefreshToken);
        return generateToken(userDetails);
    }

    @Override
    public String generateAccessTokenForAdminRegistration() {
        return Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(calculateAccessExpirationTime(accessTokenLifetime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date calculateAccessExpirationTime(int secs) {
        LocalDateTime currentTime = LocalDateTime.now();
        return toDate(currentTime.plusSeconds(secs));
    }

    private Date calculateRefreshExpirationTime(int days) {
        LocalDateTime currentTime = LocalDateTime.now();
        return toDate(currentTime.plusDays(days));
    }
}
