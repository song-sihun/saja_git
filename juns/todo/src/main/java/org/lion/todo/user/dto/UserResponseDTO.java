package org.lion.todo.user.dto;

import lombok.Builder;

@Builder
public record UserResponseDTO (
        Long userId,
        String email,
        String username
) {
}
