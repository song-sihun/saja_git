package org.lion.springsecurity.basicjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class JwtTokenizer {

    private final byte[] accessSecret;
    private final byte[] refreshSecret;
//
//    public final static Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; // 30분
//    public final static Long REFRESH_TOKEN_EXPIRE_COUNT = 7 * 24 * 60 * 60 * 1000L; // 7일

    private final long accessExpirationTime;
    private final long refreshExpirationTime;

    public JwtTokenizer(@Value("${jwt.secret-key}") String accessSecret,
                        @Value("${jwt.secret-key}") String refreshSecret,
                        @Value("${jwt.access-expiration-ms}") long accessExpirationTime,
                        @Value("${jwt.refresh-expiration-ms}") long refreshExpirationTime

    ) {
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    /**
     * Access Token 생성
     */
    public String createAccessToken(Long id, String email, String name,
                                    String username, List<String> roles) {
        return createToken(id, email, name, username, roles,
                accessExpirationTime, accessSecret);
    }

    /**
     * Refresh Token 생성
     */
    public String createRefreshToken(Long id, String email, String name,
                                     String username, List<String> roles) {
        return createToken(id, email, name, username, roles,
                refreshExpirationTime, refreshSecret);
    }

    /**
     * JWT 토큰 생성 공통 메서드
     */
    private String createToken(Long id, String email, String name, String username, List<String> roles, Long expire, byte[] secretKey) {
        Date now = new Date();
        Date expiration = new Date(now.getTime()+expire);
        return Jwts.builder()
                .subject(email)
                .claim("username",username)
                .claim("name",name)
                .claim("userId",id)
                .claim("roles",roles)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey(secretKey))
                .compact();
    }

    /**
     * 토큰에서 사용자 ID 추출
     */
    public Long getUserIdFromToken(String token) {
        String[] tokenArr = token.split(" ");
        token = tokenArr[tokenArr.length - 1];
        Claims claims = parseToken(token, accessSecret);
        return Long.valueOf((Integer) claims.get("userId"));
    }

    /**
     * 토큰 파싱 및 검증
     */
    private Claims parseToken(String token,byte[] secretKey){
        return Jwts.parser()
                .verifyWith(getSigningKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private SecretKey getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }

    /**
     * Access Token 파싱
     */
    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }

    /**
     * Refresh Token 파싱
     */
    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }

}