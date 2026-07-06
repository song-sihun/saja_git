package org.lion.minirestapi.auth.controller;

import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.jwt.dto.LoginRequestDTO;
import org.lion.minirestapi.jwt.dto.LogoutRequestDTO;
import org.lion.minirestapi.jwt.dto.RefreshRequestDTO;
import org.lion.minirestapi.jwt.dto.TokenResponseDTO;
import org.lion.minirestapi.jwt.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public TokenResponseDTO refresh(@RequestBody RefreshRequestDTO request) {
        return authService.refreshToken(request.refreshToken());
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequestDTO request) {
        authService.logout(request.refreshToken());
    }
}
