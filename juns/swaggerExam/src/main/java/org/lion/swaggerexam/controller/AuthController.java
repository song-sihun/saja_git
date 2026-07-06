package org.lion.swaggerexam.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.lion.swaggerexam.dto.LoginRequestDTO;
import org.lion.swaggerexam.dto.LoginResponseDTO;
import org.lion.swaggerexam.dto.RegisterRequestDTO;
import org.lion.swaggerexam.entity.User;
import org.lion.swaggerexam.service.UserService;
import org.lion.swaggerexam.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "Welcome",
            description = "Welcome 페이지 단순 문자열 반환"
    )
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Welcome to Spring MVC");
    }


    @Operation(
            summary = "Test",
            description = "Test 페이지 내용 없음"
    )
    @SecurityRequirement(name = "bearerAuth") // 인증이 필요한 API
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }

    @Operation(
            summary = "Register 페이지",
            description = "사용자 등록, 이메일과 비밀번호를 받아서 db에 저장함"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 성공",
                    content = @Content(
                            mediaType = "User Entity",
                            schema = @Schema(
                                    type = "User",
                                    example = "{id:1, email:email, password:password}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "회원가입 실패",
                    content = @Content(
                            mediaType = ""
                    )

            )
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDTO request){
        User createUser = userService.registerUser(request.email(),request.password());
        return ResponseEntity.ok(createUser);
    }


    @Operation(
            summary = "login 페이지",
            description = "사용자 로그인"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request){
        Long userId = 1L;
        String token = jwtUtil.generateToken(userId);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

}
