package org.lion.minirestapi.post.dto;

import lombok.*;
import org.lion.minirestapi.comment.domain.Comment;
import org.lion.minirestapi.post.domain.Post;
import org.lion.minirestapi.comment.dto.CommentResponseDTO;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostDetailResponseDTO {
    private Long id;
    private String title;
    private String userName;
    private String content;
    private List<CommentResponseDTO> comments;

    public static PostDetailResponseDTO fromEntity(Post post) {
        return PostDetailResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .userName(post.getUser().getUsername())
                .content(post.getContent())
                .comments(post.getComments().stream().filter(Comment::isActivate).map(CommentResponseDTO::fromEntity).toList())
                .build();
    }
}
