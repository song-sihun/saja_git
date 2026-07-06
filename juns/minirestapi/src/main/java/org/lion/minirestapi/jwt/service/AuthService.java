package org.lion.minirestapi.jwt.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.minirestapi.auth.service.RefreshTokenService;
import org.lion.minirestapi.base.jwt.JwtTokenizer;
import org.lion.minirestapi.jwt.dto.LoginRequestDTO;
import org.lion.minirestapi.jwt.dto.TokenResponseDTO;
import org.lion.minirestapi.user.domain.User;
import org.lion.minirestapi.user.repository.UserRepository;
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
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenizer jwtTokenizer;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.username())
                .orElseThrow(() -> new BadCredentialsException("Email or Password not correct"));
        if (!passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
            throw new BadCredentialsException("Email or Password not correct");
        }

        List<String> roles = roles(user);
        String accessToken = jwtTokenizer.createAccessToken(user.getId(), user.getEmail(), user.getName(), user.getUsername(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(user.getId(), user.getEmail(), user.getName(), user.getUsername(), roles);

        log.info("login userId={}, username={}", user.getId(), user.getUsername());
        refreshTokenService.saveOrCreateRefreshToken(user.getId(), refreshToken, jwtTokenizer.createRefreshExpiresAt());

        return new TokenResponseDTO(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponseDTO refreshToken(String refreshToken) {
        refreshTokenService.findValidToken(refreshToken);
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);
        Long userId = claims.get("userId", Long.class);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        List<String> roles = roles(user);
        String newAccessToken = jwtTokenizer.createAccessToken(user.getId(), user.getEmail(), user.getName(), user.getUsername(), roles);
        String newRefreshToken = jwtTokenizer.createRefreshToken(user.getId(), user.getEmail(), user.getName(), user.getUsername(), roles);
        refreshTokenService.saveOrCreateRefreshToken(user.getId(), newRefreshToken, jwtTokenizer.createRefreshExpiresAt());

        return new TokenResponseDTO(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenService.deleteByToken(refreshToken);
    }

    private List<String> roles(User user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
