package org.lion.minirestapi.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.lion.minirestapi.comment.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CommentResponseDTO(
        Long id,
        Long postId,
        Long userId,
        Long parentId,
        String comment,
        List<CommentResponseDTO> childrenDTO){

    public static CommentResponseDTO fromEntity(Comment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .parentId(comment.getParent() == null ? null : comment.getParent().getId())
                .comment(comment.getComment())
                .childrenDTO(comment.getChildren().stream().filter(Comment::isActivate).map(CommentResponseDTO::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
