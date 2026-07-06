package org.lion.minirestapi.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CommentCreateDTO(
        @NotBlank(message = "내용을 입력하세요.")
        String comment,
        @NotNull
        Long postId,
        Long parentID
) {
}
