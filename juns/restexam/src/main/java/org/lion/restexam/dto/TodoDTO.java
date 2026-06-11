package org.lion.restexam.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.lion.restexam.domain.Todo;

@Valid
@Getter
@Setter
public class TodoDTO {
    private Long id;
    @NotBlank
    private String description;

    public static TodoDTO fromEntity(Todo todo){
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(todo.getId());
        todoDTO.setDescription(todo.getDescription());
        return todoDTO;
    }
}
