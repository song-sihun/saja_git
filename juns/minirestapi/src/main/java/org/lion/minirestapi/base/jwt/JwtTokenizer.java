package org.lion.minirestapi.base.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtTokenizer {
    private final byte[] accessSecret;
    private final byte[] refreshSecret;
    private final long accessExpirationTime;
    private final long refreshExpirationTime;

    public JwtTokenizer(
            @Value("${jwt.secretKey}") String accessSecret,
            @Value("${jwt.refreshKey}") String refreshSecret,
            @Value("${jwt.access-expiration-ms}") long accessExpirationTime,
            @Value("${jwt.refresh-expiration-ms}") long refreshExpirationTime
    ) {
        this.accessSecret = Base64.getDecoder().decode(accessSecret);
        this.refreshSecret = Base64.getDecoder().decode(refreshSecret);
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    private SecretKey getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }

    private String createToken(Long id, String email, String name, String username, List<String> roles, long expire, byte[] secretKey) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expire);

        return Jwts.builder()
                .subject(username)
                .claim("userId", id)
                .claim("email", email)
                .claim("name", name)
                .claim("roles", roles)
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey(secretKey))
                .compact();
    }

    public String createAccessToken(Long userId, String email, String name, String username, List<String> roles) {
        return createToken(userId, email, name, username, roles, accessExpirationTime, accessSecret);
    }

    public String createRefreshToken(Long userId, String email, String name, String username, List<String> roles) {
        return createToken(userId, email, name, username, roles, refreshExpirationTime, refreshSecret);
    }

    public Claims parseAccessToken(String accessToken) {
        return Jwts.parser()
                .verifyWith(getSigningKey(accessSecret))
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    public Claims parseRefreshToken(String refreshToken) {
        return Jwts.parser()
                .verifyWith(getSigningKey(refreshSecret))
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
    }

    public LocalDateTime createRefreshExpiresAt() {
        return LocalDateTime.now().plusNanos(refreshExpirationTime * 1_000_000);
    }
}
