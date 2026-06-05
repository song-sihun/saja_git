package org.diner.dinerreserve.domain;

import lombok.*;
import org.diner.dinerreserve.config.status.Role;
import org.diner.dinerreserve.config.status.UserStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {
    @Id
    private Long id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private UserStatus status;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void delete(){
        this.status = UserStatus.DELETED;
    }

    public void block(){
        this.status = UserStatus.BLOCKED;
    }

    public void unblock(){
        this.status = UserStatus.ACTIVE;
    }

    public void deactivate(){
        this.status = UserStatus.INACTIVE;
    }
}
