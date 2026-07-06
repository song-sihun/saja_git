package org.lion.minirestapi.oauth.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.minirestapi.base.jwt.JwtTokenizer;
import org.lion.minirestapi.oauth.domain.SocialLoginInfo;
import org.lion.minirestapi.oauth.repository.SocialLoginInfoRepository;
import org.lion.minirestapi.user.domain.Role;
import org.lion.minirestapi.user.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenizer jwtTokenizer;
    private final SocialLoginInfoRepository socialLoginInfoRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String socialId = String.valueOf(attributes.get("id"));
        String provider = "github";

        SocialLoginInfo socialLoginInfo = socialLoginInfoRepository
                .findByProviderAndSocialIdWithUser(provider, socialId)
                .orElseThrow(() -> new IllegalArgumentException("소셜 로그인 정보가 없습니다."));

        User user = socialLoginInfo.getUser();

        List<Role> roleList = user.getRoles().stream().toList();

        String accessToken = jwtTokenizer.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getUsername(),
                roleList.stream().map(Role::getName).collect(Collectors.toList())
        );

        String refreshToken = jwtTokenizer.createRefreshToken(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getUsername(),
                roleList.stream().map(Role::getName).collect(Collectors.toList())
        );

//        log.info("OAuth2 success handler 실행됨");
//        log.info("authentication = {}", authentication);
//
//        log.info("provider = {}", provider);
//        log.info("socialId = {}", socialId);
//        log.info("user id = {}", user.getId());
//        log.info("user email = {}", user.getEmail());
//        log.info("user name = {}", user.getName());

        String redirectUrl = "http://localhost:5173/oauth/callback"
                + "?accessToken=" + accessToken
                + "&refreshToken=" + refreshToken;

//        log.info("redirectUrl = {}", redirectUrl);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);



    }
}
