package org.lion.todo.user.dto;

public record TokenResponseDTO(
        String accessToken,
        String refreshToken
) {
}
