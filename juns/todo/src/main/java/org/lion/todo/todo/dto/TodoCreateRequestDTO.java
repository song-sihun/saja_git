package org.lion.todo.todo.dto;

public record TodoCreateRequestDTO(
        String title,
        String description
) {
}
