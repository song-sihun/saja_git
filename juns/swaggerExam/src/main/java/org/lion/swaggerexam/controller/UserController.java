package org.lion.swaggerexam.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.lion.swaggerexam.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/test")
    @Operation(
            summary = "Test",
            description = "Test 페이지 내용 없음"
    )
    public String test(){
        return "test";
    }
}
