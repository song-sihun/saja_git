package org.lion.todo.todo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.todo.config.exception.UserNotFoundException;
import org.lion.todo.todo.domain.Todo;
import org.lion.todo.todo.dto.TodoCreateRequestDTO;
import org.lion.todo.todo.dto.TodoResponseDTO;
import org.lion.todo.todo.repository.TodoRepository;
import org.lion.todo.user.domain.User;
import org.lion.todo.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public TodoResponseDTO createTodo(TodoCreateRequestDTO requestDTO, User user) {
        log.info("createTodo {}", requestDTO.description());
        User user1 = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("Cannot find user"));
        Todo todo = Todo.builder()
                .title(requestDTO.title())
                .description(requestDTO.description())
                .user(user1)
                .isDone(false)
                .build();

        Todo savedTodo = todoRepository.save(todo);

        return TodoResponseDTO.builder().title(savedTodo.getTitle()).description(savedTodo.getDescription()).username(user1.getUsername()).build();

    }

    public List<TodoResponseDTO> getAllTodosByUser(User loginUser) {
        User user = userRepository.findById(loginUser.getId()).orElseThrow(() -> new UserNotFoundException("Cannot find user"));
        List<Todo> todos = todoRepository.findAllByUser(user);
        return todos.stream().map(todo -> (
            TodoResponseDTO.builder()
                    .id(todo.getId())
                    .title(todo.getTitle())
                    .description(todo.getDescription())
                    .isDone(todo.isDone())
                    .username(todo.getUser().getUsername())
                    .build()
            )
        ).toList();
    }


    @Transactional
    public void deleteTodo(Long id, User user) {
        log.info("deleteTodo {}", id);
        todoRepository.deleteById(id);
    }


}
