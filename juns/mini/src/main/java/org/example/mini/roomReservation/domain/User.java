package org.example.mini.roomReservation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.mini.config.user.UserRole;
import org.example.mini.config.user.UserStatus;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.status = UserStatus.ACTIVE;
        this.role = UserRole.USER;
    }

    public static User createUser(String email, String encodedPassword, String username) {
        return new User(email, encodedPassword, username);
    }

    public void deactivate() {
        this.status = UserStatus.INACTIVE;
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

}
