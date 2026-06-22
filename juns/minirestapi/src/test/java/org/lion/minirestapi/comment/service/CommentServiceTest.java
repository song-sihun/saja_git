package org.lion.minirestapi.comment.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lion.minirestapi.base.handler.UserNotFoundException;
import org.lion.minirestapi.comment.dto.CommentCreateDTO;
import org.lion.minirestapi.comment.dto.CommentResponseDTO;
import org.lion.minirestapi.post.dto.PostCreateDTO;
import org.lion.minirestapi.post.dto.PostDetailResponseDTO;
import org.lion.minirestapi.post.service.PostService;
import org.lion.minirestapi.user.domain.User;
import org.lion.minirestapi.user.dto.UserCreateDTO;
import org.lion.minirestapi.user.dto.UserResponseDTO;
import org.lion.minirestapi.user.repository.UserRepository;
import org.lion.minirestapi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createComment() {
    }

    @Test
    void findAllByParent() {
    }

    @Test
    void findAllByPost() {
    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteComment() {
    }

    @Test
    void deleteComment_excludeFromList() {
        User loginUser = createUser("commenter");
        PostDetailResponseDTO post = postService.createPost(createPostCreateDTO(), loginUser);
        CommentResponseDTO comment = commentService.createComment(createCommentCreateDTO(post.getId()), loginUser);

        commentService.deleteComment(comment.id(), loginUser);

        assertTrue(commentService.findAllByPost(PageRequest.of(0, 10), post.getId()).isEmpty());
    }

    private User createUser(String prefix) {
        UserResponseDTO userResponseDTO = userService.create(createUserCreateDTO(prefix));
        return userRepository.findById(userResponseDTO.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private UserCreateDTO createUserCreateDTO(String prefix) {
        String unique = prefix + "-" + UUID.randomUUID().toString().substring(0, 8);
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername(unique);
        userCreateDTO.setPassword("password");
        userCreateDTO.setEmail(unique + "@test.com");
        userCreateDTO.setName(prefix);
        return userCreateDTO;
    }

    private PostCreateDTO createPostCreateDTO() {
        return PostCreateDTO.builder()
                .title("title")
                .content("content")
                .build();
    }

    private CommentCreateDTO createCommentCreateDTO(Long postId) {
        return CommentCreateDTO.builder()
                .comment("comment")
                .postId(postId)
                .build();
    }
}
