package org.lion.stockrestapi.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
public class UserUpdateDTO {
    @NotBlank(message = "Not Blank")
    private String currentPassword;

    @NotBlank(message = "Not Blank")
    private String newPassword;
}
