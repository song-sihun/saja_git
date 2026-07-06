package org.lion.minirestapi.oauth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomOAuth2AuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {

        log.error("OAuth2 로그인 실패", exception);

        String errorMessage = URLEncoder.encode(
                exception.getMessage(),
                StandardCharsets.UTF_8
        );

        String redirectUrl = "http://localhost:5173/oauth/error"
                + "?error=oauth_failed"
                + "&message=" + errorMessage;

        response.sendRedirect(redirectUrl);
    }
}