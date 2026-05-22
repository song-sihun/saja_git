package org.example.springmvc.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotEmpty(message = "이름을 입력하세요")
    private String name;
    @NotEmpty(message = "비밀번호을 입력하세요")
    @Size(min = 4, max = 8)
    private String password;
    @NotEmpty(message = "이메일을 입력하세요")
    @Email
    private String email;
}
