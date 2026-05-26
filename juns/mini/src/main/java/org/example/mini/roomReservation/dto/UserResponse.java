package org.example.mini.roomReservation.dto;

import org.example.mini.config.user.UserRole;
import org.example.mini.config.user.UserStatus;
import org.example.mini.roomReservation.domain.User;

public record UserResponse(
        Long id,
        String email,
        String username,
        UserStatus userStatus,
        UserRole userRole

) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getStatus(),
                user.getRole()
        );
    }

}
