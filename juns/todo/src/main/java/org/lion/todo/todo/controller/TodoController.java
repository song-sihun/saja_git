package org.lion.todo.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.lion.todo.todo.domain.Todo;
import org.lion.todo.todo.dto.TodoCreateRequestDTO;
import org.lion.todo.todo.dto.TodoResponseDTO;
import org.lion.todo.todo.service.TodoService;
import org.lion.todo.user.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    @Operation(
            summary = "Todo 조회 페이지",
            description = "유저 별 todo 전체 목록 조회"
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
    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> getAllTodos(@AuthenticationPrincipal User loginUser) {
        List<TodoResponseDTO> todos = todoService.getAllTodosByUser(loginUser);
        return ResponseEntity.ok(todos);
    }

    @PostMapping("/add")
    public ResponseEntity<TodoResponseDTO> addTodo(@AuthenticationPrincipal User loginUser, @RequestBody TodoCreateRequestDTO todoCreateRequestDTO) {
        TodoResponseDTO todo = todoService.createTodo(todoCreateRequestDTO, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }


}
