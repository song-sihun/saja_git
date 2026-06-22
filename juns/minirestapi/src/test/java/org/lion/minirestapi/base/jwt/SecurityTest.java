package org.lion.minirestapi.base.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lion.minirestapi.user.dto.UserCreateDTO;
import org.lion.minirestapi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        createUser("owner", "owner@test.com");
        createUser("other", "other@test.com");
        createUser("username", "username@test.com");
    }

    private void createUser(String username, String email) {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername(username);
        dto.setPassword("password");
        dto.setEmail(email);
        dto.setName(username);

        userService.create(dto);
    }


    @Test
    void posts_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("NOT_FOUND_TOKEN"));
    }


    @Test
    void login_returnsTokens() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "username": "username",
                          "password": "password"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }


    @Test
    void createPost_withAccessToken_returns201() throws Exception {
        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "username": "username",
                          "password": "password"
                        }
                        """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String accessToken = objectMapper
                .readTree(loginResponse)
                .get("accessToken")
                .asString();

        mockMvc.perform(post("/posts")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "title": "테스트 게시글",
                          "content": "테스트 내용"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("테스트 게시글"))
                .andExpect(jsonPath("$.content").value("테스트 내용"))
                .andExpect(jsonPath("$.userName").value("username"));
    }


    private String loginAndGetAccessToken(
            String username,
            String password
    ) throws Exception {
        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "username": "%s",
                          "password": "%s"
                        }
                        """.formatted(username, password)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response)
                .get("accessToken")
                .asString();
    }

    @Test
    void updatePost_byOtherUser_returns403() throws Exception {
        String ownerToken = loginAndGetAccessToken("owner", "password");

        String createResponse = mockMvc.perform(post("/posts")
                        .header("Authorization", "Bearer " + ownerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "title": "원래 제목",
                          "content": "원래 내용"
                        }
                        """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long postId = objectMapper.readTree(createResponse)
                .get("id")
                .asLong();

        String otherToken = loginAndGetAccessToken("other", "password");

        mockMvc.perform(put("/posts/{postId}", postId)
                        .header("Authorization", "Bearer " + otherToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "title": "무단 수정",
                          "content": "무단 수정 내용"
                        }
                        """))
                .andExpect(status().isForbidden());
    }


    @Test
    void deletePost_excludesItFromList() throws Exception {
        String accessToken =
                loginAndGetAccessToken("owner", "password");

        String createResponse = mockMvc.perform(post("/posts")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "title": "삭제할 게시글",
                          "content": "삭제할 내용"
                        }
                        """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long postId = objectMapper.readTree(createResponse)
                .get("id")
                .asLong();

        mockMvc.perform(delete("/posts/{postId}", postId)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/posts")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath(
                                "$.content[?(@.id == %d)]",
                                postId
                        ).isEmpty()
                );

        mockMvc.perform(get("/posts/{postId}", postId)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }

}