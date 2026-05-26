package org.example.mini.roomReservation.dto;

public record UserCreateRequest(
        String email,
        String password,
        String username
) {

}
