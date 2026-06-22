package org.lion.minirestapi.base.jwt.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.minirestapi.base.handler.JwtExceptionCode;
import org.lion.minirestapi.base.jwt.JwtTokenizer;
import org.lion.minirestapi.user.domain.Role;
import org.lion.minirestapi.user.domain.User;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);
        if (StringUtils.hasText(token)) {
            try {
                // 2. 토큰 검증 및 인증 객체 생성
                getAuthentication(token);
            } catch (ExpiredJwtException e) {
                log.error("만료된 JWT 토큰입니다.", e);
                request.setAttribute("exception", JwtExceptionCode.EXPIRED_TOKEN.getCode());
            } catch (UnsupportedJwtException e) {
                log.error("지원되지 않는 JWT 토큰입니다.", e);
                request.setAttribute("exception", JwtExceptionCode.UNSUPPORTED_TOKEN.getCode());
            } catch (MalformedJwtException e) {
                log.error("JWT 토큰이 올바르지 않습니다.", e);
                request.setAttribute("exception", JwtExceptionCode.INVALID_TOKEN.getCode());
            } catch (IllegalArgumentException e) {
                log.error("JWT 토큰이 비어있습니다.", e);
                request.setAttribute("exception", JwtExceptionCode.INVALID_TOKEN.getCode());
            } catch (Exception e) {
                log.error("JWT 토큰 검증 중 오류가 발생했습니다.", e);
                request.setAttribute("exception", "Exception");
            }
        }

        filterChain.doFilter(request, response);

    }

    private void getAuthentication(String token) {
        Claims claims = jwtTokenizer.parseAccessToken(token);
        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);
        String name = claims.get("name", String.class);
        String email = claims.get("email", String.class);
        List<GrantedAuthority> authorities = getAuthorities(claims);

        User user = new User(userId, username, name, "", email, authorities.
                stream().map(
                        GrantedAuthority::getAuthority
                ).filter(Objects::nonNull)
                .map(authority -> authority.replace("ROLE_", ""))
                .map(Role::new)
                .collect(Collectors.toSet())
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private List<GrantedAuthority> getAuthorities(Claims claims) {
        List<String> roles = claims.get("roles", List.class);

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}
