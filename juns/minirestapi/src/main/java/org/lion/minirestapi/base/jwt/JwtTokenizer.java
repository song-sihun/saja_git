package org.lion.minirestapi.base.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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


    private SecretKey getSigningKey(byte[] secretKey){
        return Keys.hmacShaKeyFor(secretKey);
    }


    private String creteToken(Long id, String email, String name, String username, List<String> roles, Long expire, byte[] secretKey){
        Date now = new Date();
        Date expiration = new Date(now.getTime()+expire);

        return Jwts.builder()
                .subject(username)
                .claim("userId", id)
                .claim("email", email)
                .claim("name", name)
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey(secretKey))
                .compact();
    }


    public String createAccessToken(Long userId, String email, String name, String username, List<String> roles) {
        return creteToken(userId, email, name, username, roles, accessExpirationTime, accessSecret);
    }

    public String createRefreshToken(Long userId, String email, String name, String username, List<String> roles) {
        return creteToken(userId, email, name, username, roles, refreshExpirationTime, refreshSecret);
    }

    private Claims parseToken(String token, byte[] secretKey) {
        return Jwts.parser()
                .verifyWith(getSigningKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }

    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }
}

