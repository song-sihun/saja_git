package org.lion.minirestapi.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.user.domain.User;
import org.lion.minirestapi.user.dto.UserCreateDTO;
import org.lion.minirestapi.user.dto.UserResponseDTO;
import org.lion.minirestapi.user.dto.UserUpdateDTO;
import org.lion.minirestapi.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> signup(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO user = userService.create(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyInfo(@AuthenticationPrincipal User loginUser) {
        return ResponseEntity.ok(UserResponseDTO.fromEntity(loginUser));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<UserResponseDTO> update(@Valid @RequestBody UserUpdateDTO userUpdateDTO, @AuthenticationPrincipal User loginUser) {
        return ResponseEntity.ok(userService.updatePassword(loginUser.getId(), userUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User loginUser) {
        userService.deleteById(id, loginUser);
        return ResponseEntity.noContent().build();
    }
}
