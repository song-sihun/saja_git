package org.lion.todo.user.service;

import lombok.RequiredArgsConstructor;
import org.lion.todo.user.domain.RefreshToken;
import org.lion.todo.user.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveOrCreateRefreshToken(Long userId, String refreshToken) {
        RefreshToken newRefreshToken = refreshTokenRepository.findByUserId(userId)
                .orElseGet(() -> RefreshToken.builder()
                        .userId(userId)
                        .token(refreshToken)
                        .build()
                );

        newRefreshToken.updateToken(refreshToken);
        refreshTokenRepository.save(newRefreshToken);
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid token"));
    }

    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
