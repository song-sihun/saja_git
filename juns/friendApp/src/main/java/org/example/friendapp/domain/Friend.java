package org.example.friendapp.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("friends")
public class Friend {
    @Id
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;

    public Friend(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
