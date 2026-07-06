package org.lion.minirestapi.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.comment.dto.CommentCreateDTO;
import org.lion.minirestapi.comment.dto.CommentResponseDTO;
import org.lion.minirestapi.comment.dto.CommentUpdateDTO;
import org.lion.minirestapi.comment.service.CommentService;
import org.lion.minirestapi.user.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateDTO request,
            @AuthenticationPrincipal User loginUser
    ) {
        CommentCreateDTO commentCreateDTO = new CommentCreateDTO(request.comment(), postId, request.parentID());
        CommentResponseDTO commentResponseDTO = commentService.createComment(commentCreateDTO, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDTO);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> editComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateDTO commentUpdateDTO,
            @AuthenticationPrincipal User loginUser
    ) {
        return ResponseEntity.ok(commentService.updateComment(commentUpdateDTO, commentId, loginUser));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal User loginUser) {
        commentService.deleteComment(commentId, loginUser);
        return ResponseEntity.noContent().build();
    }
}
