package org.lion.restexam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.lion.restexam.dto.UserDTO;

@ToString
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    private String name="guest";
    @Builder.Default
    private Integer age=18;

    public User (String name, Integer age){
        this.name = name;
        this.age = age;
    }

    public static User fromDTO(UserDTO userDTO){
        return User.builder()
                .name(userDTO.getName())
                .age(userDTO.getAge())
                .build();
    }
}
