package org.lion.minirestapi.jwt.dto;

public record LogoutRequestDTO(
        String refreshToken
) {
}
