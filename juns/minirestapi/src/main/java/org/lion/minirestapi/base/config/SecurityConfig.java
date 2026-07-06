package org.lion.minirestapi.base.config;

import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.base.handler.CustomAuthenticationEntryPoint;
import org.lion.minirestapi.oauth.security.CustomOAuth2AuthenticationFailureHandler;
import org.lion.minirestapi.oauth.security.CustomOAuth2AuthenticationSuccessHandler;
import org.lion.minirestapi.base.jwt.JwtTokenizer;
import org.lion.minirestapi.base.jwt.filter.JwtAuthenticationFilter;
import org.lion.minirestapi.oauth.service.SocialLoginInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenizer  jwtTokenizer;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final SocialLoginInfoService socialLoginInfoService;
    private final CustomOAuth2AuthenticationSuccessHandler customOAuth2AuthenticationSuccessHandler;
    private final CustomOAuth2AuthenticationFailureHandler customOAuth2AuthenticationFailureHandler;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/", "/auth/login", "/auth/refresh",
                                "/users", "/oauth2/**", "/login/oauth2/code/github",
                                "/saveSocialUser",
                                "/uploads/**"
                        ).permitAll()
                        .requestMatchers("/posts/**", "/users/**", "/comments/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenizer), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(this.oAuth2UserService())
                        )
                        .successHandler(customOAuth2AuthenticationSuccessHandler)
                        .failureHandler(customOAuth2AuthenticationFailureHandler)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                );
        return httpSecurity.build();

    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); //쿠키 허용
        configuration.setAllowedOriginPatterns(
                List.of(
                        "http://localhost:5173",
                        "http://localhost:8080"
                )
        ); // 도메인 허용
        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;

    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return  oAuth2UserRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(oAuth2UserRequest);

            // 소셜 로그인이 될 때 해야할일 구현
            String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
            String socialId = String.valueOf(oAuth2User.getAttributes().get("id"));
            String username = String.valueOf(oAuth2User.getAttributes().get("login"));
            Object emailObj = oAuth2User.getAttributes().get("email");
            String email = emailObj != null
                    ? String.valueOf(emailObj)
                    : username + "@github.local";
            String avatarUrl = String.valueOf(oAuth2User.getAttributes().get("avatar_url"));

            socialLoginInfoService.saveOrUpdateUser(socialId, provider, username, email, avatarUrl);


            return oAuth2User;
        };
    }
}
