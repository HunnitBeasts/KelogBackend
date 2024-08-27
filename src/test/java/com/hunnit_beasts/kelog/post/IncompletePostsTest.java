package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class IncompletePostsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostService postService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private Long userId;
    private String token;

    @BeforeEach
    void setUp() {
        // Create a test user
        UserCreateRequestDTO dto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        userId = authService.signUp(dto).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        // Create some incomplete posts
        for (int i = 0; i < 15; i++) {
            PostCreateRequestDTO postDto = PostCreateRequestDTO.builder()
                    .title("Incomplete testTitle" + i)
                    .type(PostType.INCOMPLETE)
                    .thumbImage("testThumbImage" + i)
                    .isPublic(Boolean.FALSE)
                    .shortContent("Incomplete testShortContent" + i)
                    .url("testUrl" + i)
                    .content("Incomplete testContent" + i)
                    .tags(List.of("incompleteTag" + (i % 3)))
                    .build();

            postService.postCreate(userId, postDto);
        }
    }

    @Test
    @DisplayName("미완성 게시물 조회 테스트")
    void getIncompletePosts() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/incomplete", userId)
                        .param("sort", "reg-date")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.count").value(greaterThan(0)))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts", hasSize(10)))
                .andExpect(jsonPath("$.posts[0].postId").isNumber())
                .andExpect(jsonPath("$.posts[0].postThumbImage").isString())
                .andExpect(jsonPath("$.posts[0].title").isString())
                .andExpect(jsonPath("$.posts[0].shortContent").isString())
                .andExpect(jsonPath("$.posts[0].regDate").isString())
                .andExpect(jsonPath("$.posts[0].commentCount").isNumber())
                .andExpect(jsonPath("$.posts[0].userThumbImage").isString())
                .andExpect(jsonPath("$.posts[0].nickname").isString())
                .andExpect(jsonPath("$.posts[0].loginId").isString())
                .andExpect(jsonPath("$.posts[0].likeCount").isNumber())
                .andExpect(jsonPath("$.posts[*].title", everyItem(startsWith("Incomplete"))));
    }

    @Test
    @DisplayName("미완성 게시물 조회 - 페이징 테스트")
    void getIncompletePostsWithPaging() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/incomplete", userId)
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(10)))
                .andReturn();

        mockMvc.perform(get("/posts/{user-id}/incomplete", userId)
                        .param("page", "2")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(5)))
                .andReturn();
    }

    @Test
    @DisplayName("미완성 게시물 조회 - 정렬 테스트 (등록일 내림차순)")
    void getIncompletePostsSortedByRegDate() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/incomplete", userId)
                        .param("sort", "reg-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts").isNotEmpty())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    JsonNode root = objectMapper.readTree(content);
                    JsonNode posts = root.get("posts");

                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

                    for (int i = 0; i < posts.size() - 1; i++) {
                        LocalDateTime date1 = LocalDateTime.parse(posts.get(i).get("regDate").asText(), formatter);
                        LocalDateTime date2 = LocalDateTime.parse(posts.get(i+1).get("regDate").asText(), formatter);
                        assertTrue(date1.isAfter(date2) || date1.isEqual(date2),
                                String.format("Date at index %d is not after or equal to date at index %d", i, i+1));
                    }
                })
                .andReturn();
    }
}