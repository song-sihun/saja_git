package org.lion.todo.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.lion.todo.user.dto.*;
import org.lion.todo.user.service.AuthService;
import org.lion.todo.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO registerDTO){
        UserResponseDTO user = userService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginRequestDTO request){
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public TokenResponseDTO refresh(@RequestBody RefreshRequestDTO request){
        return authService.refreshToken(request.refreshToken());
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequestDTO request){
        authService.logout(request.refreshToken());
    }

    private String getRefreshToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh_token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
