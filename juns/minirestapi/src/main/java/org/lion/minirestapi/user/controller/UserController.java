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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/myinfo")
    public ResponseEntity<UserResponseDTO> getMyInfo(@AuthenticationPrincipal User loginUser) {
        return ResponseEntity.ok().body(UserResponseDTO.fromEntity(loginUser));
    }


    @PutMapping("/updatepassword")
    public ResponseEntity<UserResponseDTO> update(@Valid @RequestBody UserUpdateDTO userUpdateDTO, @AuthenticationPrincipal User loginUser) {
        Long currentUserId = loginUser.getId();
        UserResponseDTO updatedUser = userService.updatePassword(currentUserId, userUpdateDTO);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User loginUser) {
        userService.deleteById(id, loginUser);
        return ResponseEntity.noContent().build();
    }

}
