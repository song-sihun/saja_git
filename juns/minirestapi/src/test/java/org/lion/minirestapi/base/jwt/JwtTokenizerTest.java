package org.lion.minirestapi.base.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class JwtTokenizerTest {

    private static final String ACCESS_SECRET = encode("access-secret-key-for-jwt-test-123");
    private static final String REFRESH_SECRET = encode("refresh-secret-key-for-jwt-test-12");
    private static final long ACCESS_EXPIRATION_MS = 60_000L;
    private static final long REFRESH_EXPIRATION_MS = 120_000L;

    @Test
    void createsAndParsesAccessToken() {
        JwtTokenizer tokenizer = createTokenizer();
        String token = tokenizer.createAccessToken(
                1L,
                "user@example.com",
                "User Name",
                "username",
                List.of("ROLE_USER", "ROLE_ADMIN")
        );
        Claims claims = tokenizer.parseAccessToken(token);

        assertEquals("user@example.com", claims.getSubject());
        assertEquals(1L, claims.get("userId", Long.class));
        assertEquals("User Name", claims.get("name", String.class));
        assertEquals("username", claims.get("username", String.class));
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), claims.get("roles", List.class));

        long actualLifetime = claims.getExpiration().getTime() - claims.getIssuedAt().getTime();
        assertEquals(ACCESS_EXPIRATION_MS, actualLifetime);
    }

    @Test
    void accessAndRefreshTokensUseDifferentSigningKeys() {
        JwtTokenizer tokenizer = createTokenizer();
        String accessToken = tokenizer.createAccessToken(
                1L, "user@example.com", "User Name", "username", List.of("ROLE_USER")
        );
        String refreshToken = tokenizer.createRefreshToken(
                1L, "user@example.com", "User Name", "username", List.of("ROLE_USER")
        );

        assertThrows(JwtException.class, () -> tokenizer.parseRefreshToken(accessToken));
        assertThrows(JwtException.class, () -> tokenizer.parseAccessToken(refreshToken));

        Claims refreshClaims = tokenizer.parseRefreshToken(refreshToken);
        long actualLifetime = refreshClaims.getExpiration().getTime()
                - refreshClaims.getIssuedAt().getTime();
        assertEquals(REFRESH_EXPIRATION_MS, actualLifetime);
    }

    @Test
    void rejectsExpiredToken() throws InterruptedException {
        JwtTokenizer tokenizer = new JwtTokenizer(
                ACCESS_SECRET,
                REFRESH_SECRET,
                1L,
                REFRESH_EXPIRATION_MS
        );
        String token = tokenizer.createAccessToken(
                1L, "user@example.com", "User Name", "username", List.of("ROLE_USER")
        );

        Thread.sleep(10L);

        assertThrows(ExpiredJwtException.class, () -> tokenizer.parseAccessToken(token));
    }

    private JwtTokenizer createTokenizer() {
        return new JwtTokenizer(
                ACCESS_SECRET,
                REFRESH_SECRET,
                ACCESS_EXPIRATION_MS,
                REFRESH_EXPIRATION_MS
        );
    }

    private static String encode(String secret) {
        return Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }


}
