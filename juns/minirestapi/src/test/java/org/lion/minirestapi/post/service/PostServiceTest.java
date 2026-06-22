package org.lion.minirestapi.post.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lion.minirestapi.base.handler.UserNotFoundException;
import org.lion.minirestapi.post.dto.PostCreateDTO;
import org.lion.minirestapi.post.dto.PostDetailResponseDTO;
import org.lion.minirestapi.post.dto.PostUpdateDTO;
import org.lion.minirestapi.user.domain.User;
import org.lion.minirestapi.user.dto.UserCreateDTO;
import org.lion.minirestapi.user.dto.UserResponseDTO;
import org.lion.minirestapi.user.repository.UserRepository;
import org.lion.minirestapi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {
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
    void createPost() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findPostById() {
    }

    @Test
    void deactivatePostById() {
    }

    @Test
    void deletePostById() {
    }

    @Test
    void updatePostById() {
    }

    @Test
    void updatePostById_otherUser_forbidden() {
        User owner = createUser("owner");
        User otherUser = createUser("other");
        PostDetailResponseDTO post = postService.createPost(createPostCreateDTO(), owner);
        PostUpdateDTO postUpdateDTO = PostUpdateDTO.builder()
                .title("updated title")
                .content("updated content")
                .build();

        assertThrows(AccessDeniedException.class, () -> {
            postService.updatePostById(post.getId(), postUpdateDTO, otherUser);
        });
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
        return PostCreateDTO.builder().title("new title").content("new content").build();
    }
}
