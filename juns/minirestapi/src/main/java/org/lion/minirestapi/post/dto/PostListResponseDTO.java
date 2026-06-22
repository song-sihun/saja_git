package org.lion.minirestapi.post.dto;

import lombok.*;
import org.lion.minirestapi.comment.domain.Comment;
import org.lion.minirestapi.post.domain.Post;
import org.lion.minirestapi.comment.dto.CommentResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostListResponseDTO {
    private Long id;
    private String title;
    private String userName;
    private LocalDateTime createdAt;

    public static PostListResponseDTO fromEntity(Post post) {
        return PostListResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .userName(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
