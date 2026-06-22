package org.lion.springsecurity.basicjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

@Slf4j
public class JwtExample {
    public static void main(String[] args) throws NoSuchAlgorithmException {
//        SecretKey secretKey = Keys.hmacShaKeyFor("secrettomakekjaskldjklasdjlwasdadwe1d2a3sd1asdas5da23s1da1s3d".getBytes());
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        System.out.println("secretKey: " + secretKey);

        String jwt = Jwts.builder()
                .issuer("myIssuer") // 발급 주체
                .subject("mySubject") // login id
                .audience().add("server").and() // 누구를 위한 것인지?? 누구가 뭔데
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 만료시간
                .issuedAt(new Date()) // 토큰 발급시간
                .notBefore(new Date()) // 이 시간 전에는 사용불가?
                .claim("myClaim", "myClaimValue")
                .signWith(secretKey)
                .compact();


        System.out.println("jwt: " + jwt);

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

        System.out.println("claims: " + claims);
    }
}
