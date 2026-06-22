package org.lion.minirestapi.post.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record PostCreateDTO(
        @NotBlank(message = "제목을 입력하세요")
        String title,
        @NotBlank(message = "내용을 입력하세요")
        String content
) {

}
