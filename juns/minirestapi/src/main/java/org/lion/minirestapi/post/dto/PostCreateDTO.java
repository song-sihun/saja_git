package org.lion.minirestapi.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PostCreateDTO(
        @NotBlank(message = "제목을 입력하세요.")
        String title,
        @NotBlank(message = "내용을 입력하세요.")
        String content,
        @NotBlank(message = "카테고리를 입력하세요.")
        String category
) {
}
