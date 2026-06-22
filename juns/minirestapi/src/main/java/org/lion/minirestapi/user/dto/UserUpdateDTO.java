package org.lion.minirestapi.user.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserUpdateDTO {
    @NotBlank(message = "현재 비밀번호는 필수입니다.")
    private String currentPassword;
    @NotBlank(message = "변경할 비밀번호를 입력하세요")
    private String newPassword;
}
