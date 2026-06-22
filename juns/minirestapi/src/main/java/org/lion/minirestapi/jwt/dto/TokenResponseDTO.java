package org.lion.minirestapi.jwt.dto;

public record TokenResponseDTO(
        String accessToken,
        String refreshToken
) {
}
