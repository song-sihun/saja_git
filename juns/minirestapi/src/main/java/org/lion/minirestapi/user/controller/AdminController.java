package org.lion.minirestapi.user.controller;

import lombok.RequiredArgsConstructor;
import org.lion.minirestapi.post.dto.PostDetailResponseDTO;
import org.lion.minirestapi.post.service.PostService;
import org.lion.minirestapi.user.domain.User;
import org.lion.minirestapi.user.dto.UserResponseDTO;
import org.lion.minirestapi.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDTO>> getUsers(@AuthenticationPrincipal User loginUser, Pageable pageable) {

        Page<UserResponseDTO> userResponseDTOPage = userService.findAll(loginUser, pageable);

        return ResponseEntity.ok(userResponseDTOPage);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @AuthenticationPrincipal User loginUser) {
        userService.deleteById(id, loginUser);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteBoardAdmin(@PathVariable Long postId, @AuthenticationPrincipal User loginUser) {
        postService.deletePostById(postId, loginUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostDetailResponseDTO>> getPosts(@AuthenticationPrincipal User loginUser, @PageableDefault(size = 10) Pageable pageable) {
        Page<PostDetailResponseDTO> postResponseDTOPage = postService.findAllAdmin(loginUser, pageable);
        return ResponseEntity.ok(postResponseDTOPage);
    }
}
