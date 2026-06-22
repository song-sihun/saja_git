package org.lion.stockrestapi.user.controller;

import lombok.RequiredArgsConstructor;
import org.lion.stockrestapi.user.domain.User;
import org.lion.stockrestapi.user.dto.UserCreateDTO;
import org.lion.stockrestapi.user.dto.UserResponseDTO;
import org.lion.stockrestapi.user.dto.UserUpdateDTO;
import org.lion.stockrestapi.user.repository.UserRepository;
import org.lion.stockrestapi.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO, @AuthenticationPrincipal User loginUser) {
        UserResponseDTO userResponseDTO = userService.updateUserPassword(userId, userUpdateDTO, loginUser);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable Long userId,  @AuthenticationPrincipal User loginUser) {
        userService.deleteUser(userId, loginUser);
        return ResponseEntity.noContent().build();
    }


}
