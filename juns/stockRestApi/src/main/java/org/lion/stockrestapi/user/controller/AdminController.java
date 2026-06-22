package org.lion.stockrestapi.user.controller;

import lombok.RequiredArgsConstructor;
import org.lion.stockrestapi.user.domain.User;
import org.lion.stockrestapi.user.dto.UserResponseDTO;
import org.lion.stockrestapi.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(@AuthenticationPrincipal User loginUser, @PageableDefault Pageable pageable) {
        Page<UserResponseDTO> userResponseDTOPage = userService.findAll(loginUser, pageable);
        return ResponseEntity.ok(userResponseDTOPage);
    }
}
