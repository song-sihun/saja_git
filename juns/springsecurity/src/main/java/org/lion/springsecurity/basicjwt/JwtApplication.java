package org.lion.springsecurity.basicjwt;

import io.jsonwebtoken.Claims;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            JwtTokenizer jwtTokenizer = ctx.getBean(JwtTokenizer.class);

            String accessToken = jwtTokenizer.createAccessToken(
                    1L,
                    "test@test.com",
                    "홍길동",
                    "testuser",
                    List.of("ROLE_USER")
            );
            System.out.println("accessToken = " + accessToken);

            String refreshToken = jwtTokenizer.createRefreshToken(
                    1L,
                    "test@test.com",
                    "홍길동",
                    "testuser",
                    List.of("ROLE_USER")
            );

            System.out.println("refreshToken = " + refreshToken);




            Claims claims = jwtTokenizer.parseAccessToken(accessToken);

            System.out.println("subject = " + claims.getSubject());
            System.out.println("userId = " + claims.get("userId"));
            System.out.println("email = " + claims.getSubject());
            System.out.println("name = " + claims.get("name"));
            System.out.println("username = " + claims.get("username"));
            System.out.println("roles = " + claims.get("roles"));
            System.out.println("issuedAt = " + claims.getIssuedAt());
            System.out.println("expiration = " + claims.getExpiration());


            Claims claims2 = jwtTokenizer.parseRefreshToken(refreshToken);
            System.out.println("subject = " + claims2.get("subject"));
            System.out.println("userId = " + claims2.get("userId"));
            System.out.println("email = " + claims2.get("email"));

        };
    }
}