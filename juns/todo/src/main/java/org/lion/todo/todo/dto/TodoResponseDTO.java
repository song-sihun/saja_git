package org.lion.todo.todo.dto;

import lombok.Builder;

@Builder
public record TodoResponseDTO(
        Long id,
        String title,
        String description,
        boolean isDone,
        String username
) {
}
