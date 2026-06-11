package org.lion.restexam.controller;

import lombok.RequiredArgsConstructor;
import org.lion.restexam.dto.TodoDTO;
import org.lion.restexam.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos(){
        List<TodoDTO> todos = todoService.findAll();
        return ResponseEntity.ok(todos);
    }

    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@RequestBody TodoDTO todoDTO){
        TodoDTO todo = todoService.save(todoDTO);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable Long id, @RequestBody TodoDTO todoDTO){
        TodoDTO todo = todoService.update(id, todoDTO);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
