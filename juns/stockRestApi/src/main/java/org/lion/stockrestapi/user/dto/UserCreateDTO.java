package org.lion.stockrestapi.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserCreateDTO {
    @NotBlank(message = "Not Blank")
    private String username;

    @NotBlank(message = "Not Blank")
    private String password;

    @NotBlank(message = "Not Blank")
    private String name;

    @NotBlank(message = "Not Blank")
    private String email;
}
