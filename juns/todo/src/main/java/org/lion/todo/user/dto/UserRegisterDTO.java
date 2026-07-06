package org.lion.todo.user.dto;

public record UserRegisterDTO (
        String email,
        String username,
        String password
) {
}
