package org.lion.todo.user.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.todo.config.jwt.JwtTokenizer;
import org.lion.todo.user.domain.RefreshToken;
import org.lion.todo.user.domain.User;
import org.lion.todo.user.dto.LoginRequestDTO;
import org.lion.todo.user.dto.TokenResponseDTO;
import org.lion.todo.user.repository.RefreshTokenRepository;
import org.lion.todo.user.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenizer jwtTokenizer;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.username()).orElseThrow(() -> new BadCredentialsException("Email or Password not correct"));

        if(!passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
            throw new BadCredentialsException("Email or Password not correct\"");
        }

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String accessToken = jwtTokenizer.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                roles
        );

        String refreshToken = jwtTokenizer.createRefreshToken(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                roles
        );


        log.info("login userId={}, username={}", user.getId(), user.getUsername());

        refreshTokenService.saveOrCreateRefreshToken(user.getId(), refreshToken);

        return new TokenResponseDTO(accessToken, refreshToken);

    }

    public TokenResponseDTO refreshToken(String refreshToken) {

        RefreshToken refreshToken1 = refreshTokenService.findByToken(refreshToken);
        if(refreshToken1 == null) {
            throw new BadCredentialsException("Refresh token not found");
        }

        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);

        Long userId = claims.get("userId", Long.class);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String newAccessToken = jwtTokenizer.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                roles
        );

        return new TokenResponseDTO(newAccessToken, refreshToken);

    }

    public void logout(String refreshToken) {
        refreshTokenService.deleteByToken(refreshToken);
    }

}
