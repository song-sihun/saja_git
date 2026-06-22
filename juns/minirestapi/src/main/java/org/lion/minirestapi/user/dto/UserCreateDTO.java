package org.lion.minirestapi.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lion.minirestapi.user.domain.Role;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Getter
@Setter
@ToString
@Validated
public class UserCreateDTO {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;
}
