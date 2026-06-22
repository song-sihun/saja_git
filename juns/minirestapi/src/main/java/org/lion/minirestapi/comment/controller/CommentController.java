package org.lion.minirestapi.comment.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.comment.domain.Comment;
import org.lion.minirestapi.comment.dto.CommentCreateDTO;
import org.lion.minirestapi.comment.dto.CommentResponseDTO;
import org.lion.minirestapi.comment.dto.CommentUpdateDTO;
import org.lion.minirestapi.comment.service.CommentService;
import org.lion.minirestapi.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentCreateDTO commentCreateDTO, @AuthenticationPrincipal User loginUser) {
        CommentResponseDTO commentResponseDTO = commentService.createComment(commentCreateDTO, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDTO);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> editComment(@PathVariable Long commentId, @Valid @RequestBody CommentUpdateDTO commentUpdateDTO, @AuthenticationPrincipal User loginUser) {
        CommentResponseDTO commentResponseDTO = commentService.updateComment(commentUpdateDTO, commentId, loginUser);
        return ResponseEntity.ok(commentResponseDTO);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal User loginUser) {
        commentService.deleteComment(commentId, loginUser);
        return ResponseEntity.noContent().build();
    }

}
