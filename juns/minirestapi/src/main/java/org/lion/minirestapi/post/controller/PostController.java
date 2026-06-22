package org.lion.minirestapi.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.comment.dto.CommentResponseDTO;
import org.lion.minirestapi.comment.service.CommentService;
import org.lion.minirestapi.post.dto.PostCreateDTO;
import org.lion.minirestapi.post.dto.PostDetailResponseDTO;
import org.lion.minirestapi.post.dto.PostListResponseDTO;
import org.lion.minirestapi.post.dto.PostUpdateDTO;
import org.lion.minirestapi.post.service.PostService;
import org.lion.minirestapi.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/{postId}/comments")
    public ResponseEntity<Page<CommentResponseDTO>> getCommentById(
            @PathVariable Long postId,
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {
        Page<CommentResponseDTO> commentResponseDTOPage = commentService.findAllByPost(pageable, postId);
        return ResponseEntity.ok(commentResponseDTOPage);
    }

    @GetMapping
    public ResponseEntity<Page<PostListResponseDTO>> getBoard(
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        Page<PostListResponseDTO> boardResponseDTOS = postService.findAll(pageable);
        return ResponseEntity.ok(boardResponseDTOS);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDTO> getBoard(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.findPostById(postId));
    }

    @PostMapping
    public ResponseEntity<PostDetailResponseDTO> createBoard(@Valid @RequestBody PostCreateDTO postCreateDTO, @AuthenticationPrincipal User loginUser) {
        PostDetailResponseDTO postDetailResponseDTO = postService.createPost(postCreateDTO, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDetailResponseDTO);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDTO> updateBoard(@PathVariable Long postId, @Valid @RequestBody PostUpdateDTO postUpdateDTO, @AuthenticationPrincipal User loginUser) {
        PostDetailResponseDTO postDetailResponseDTO = postService.updatePostById(postId, postUpdateDTO, loginUser);
        return ResponseEntity.ok(postDetailResponseDTO);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteBoard(@AuthenticationPrincipal User loginUser, @PathVariable Long postId) {
        postService.deactivatePostById(postId, loginUser);
        return ResponseEntity.noContent().build();
    }



}
