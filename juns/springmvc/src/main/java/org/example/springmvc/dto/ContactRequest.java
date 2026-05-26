package org.example.springmvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactRequest(
        @NotBlank(message = "이름은 필수 입력 입니다.")
        String username,
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일은 필수 입력 입니다.")
        String email,
        @NotBlank(message = "메세지는 필수 입력 입니다.")
        String message
) {
}
