package org.lion.restexam.dto;

import lombok.Getter;
import lombok.Setter;
import org.lion.restexam.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private Integer age;
    private LocalDateTime createdAt;

    public static UserDTO fromEntity(UserDTO userDTO){
        UserDTO userDTO1 = new UserDTO();

        userDTO1.setId(userDTO.getId());
        userDTO1.setName(userDTO.getName());
        userDTO1.setAge(userDTO.getAge());
        userDTO1.setCreatedAt(userDTO.getCreatedAt());

        return userDTO1;
    }
}
