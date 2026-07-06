package org.lion.swaggerexam.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 요청 DTO")
public record RegisterRequestDTO(
        @Schema(description = "사용자 이메일", example = "user@example.com")
        String email,
        @Schema(description = "사용자 비밀번호", example = "password123")
        String password
) {
}
