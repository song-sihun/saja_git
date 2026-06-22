package org.lion.minirestapi.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public record CommentUpdateDTO(
        @NotBlank
        String comment
){
}
