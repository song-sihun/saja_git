package org.lion.minirestapi.auth.service;

import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.auth.domain.RefreshToken;
import org.lion.minirestapi.auth.repository.RefreshTokenRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveOrCreateRefreshToken(Long userId, String refreshToken, LocalDateTime expiresAt) {
        RefreshToken token = refreshTokenRepository.findByUserId(userId)
                .orElseGet(() -> RefreshToken.builder()
                        .userId(userId)
                        .token(refreshToken)
                        .expiresAt(expiresAt)
                        .build());

        token.updateToken(refreshToken, expiresAt);
        refreshTokenRepository.save(token);
    }

    public RefreshToken findValidToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));
        if (refreshToken.isExpired()) {
            throw new BadCredentialsException("Expired refresh token");
        }
        return refreshToken;
    }

    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
