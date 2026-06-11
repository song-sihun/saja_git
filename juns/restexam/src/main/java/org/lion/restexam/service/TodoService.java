package org.lion.restexam.service;

import lombok.RequiredArgsConstructor;
import org.lion.restexam.config.CustomException;
import org.lion.restexam.config.ExceptionType;
import org.lion.restexam.domain.Todo;
import org.lion.restexam.dto.TodoDTO;
import org.lion.restexam.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoDTO findById(Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionType.TodoNotFound.getMessage()));
        return TodoDTO.fromEntity(todo);
    }

    public List<TodoDTO> findAll(){
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map(TodoDTO::fromEntity).toList();
    }

    @Transactional
    public TodoDTO save(TodoDTO todoDTO){
        Todo todo = todoRepository.save(Todo.fromDTO(todoDTO));
        return TodoDTO.fromEntity(todo);
    }

    @Transactional
    public void deleteById(Long id){
        todoRepository.deleteById(id);
    }

    @Transactional
    public TodoDTO update(Long id, TodoDTO todoDTO){
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionType.TodoNotFound.getMessage()));
        if (todo.getDescription() != null){
            todo.setDescription(todoDTO.getDescription());
        }
        return TodoDTO.fromEntity(todo);
    }

}
