package com.hunnit_beasts.kelog.post;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class RecentPostTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @Autowired
    JwtUtil jwtUtil;

    private final List<Long> userIds = new ArrayList<>();
    private final List<Long> postIds = new ArrayList<>();
    String token;
    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            UserCreateRequestDTO dto = UserCreateRequestDTO.builder()
                    .userId("testUserId" + i)
                    .password("testPassword")
                    .nickname("testNickname" + i)
                    .briefIntro("testBriefIntro")
                    .email("testEmail" + i)
                    .build();

            Long userId = authService.signUp(dto).getId();
            userIds.add(userId);
        }

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userIds.getFirst())
                .userId("testUserId0")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        for (int i = 0; i < 30; i++) {
            Long authorId = userIds.get(i % 10);
            PostCreateRequestDTO postDto = PostCreateRequestDTO.builder()
                    .title("testTitle" + i)
                    .type(PostType.NORMAL)
                    .thumbImage("testThumbImage" + i)
                    .isPublic(Boolean.TRUE)
                    .shortContent("testShortContent" + i)
                    .url("testUrl" + i)
                    .content("testContent" + i)
                    .tags(Arrays.asList("tag" + (i % 5), "tag" + ((i + 1) % 5)))
                    .build();

            Long postId = postService.postCreate(authorId, postDto).getId();
            postIds.add(postId);
        }

        Long firstUserId = userIds.getFirst();
        for (int i = 0; i < 15; i++) {
            Long postId = postIds.get(random.nextInt(postIds.size()));
            postService.recentViewAdd(firstUserId, postId);
        }
    }

    @Test
    @DisplayName("최근 본 게시물 리스트 조회 테스트")
    void getRecentPostList() throws Exception {
        Long firstUserId = userIds.getFirst();
        mockMvc.perform(get("/posts/{user-id}/reading", firstUserId)
                        .param("sort", "reg-date")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.count").value(lessThanOrEqualTo(15)))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts", hasSize(lessThanOrEqualTo(10))))
                .andExpect(jsonPath("$.posts[0].postId").isNumber())
                .andExpect(jsonPath("$.posts[0].postThumbImage").isString())
                .andExpect(jsonPath("$.posts[0].title").isString())
                .andExpect(jsonPath("$.posts[0].shortContent").isString())
                .andExpect(jsonPath("$.posts[0].regDate").isString())
                .andExpect(jsonPath("$.posts[0].commentCount").isNumber())
                .andExpect(jsonPath("$.posts[0].userThumbImage").isString())
                .andExpect(jsonPath("$.posts[0].nickname").isString())
                .andExpect(jsonPath("$.posts[0].likeCount").isNumber())
                .andReturn();
    }

    @Test
    @DisplayName("최근 본 게시물 리스트 조회 - 검색 테스트 (요구 없긴함)")
    void getRecentPostListWithSearch() throws Exception {
        Long firstUserId = userIds.getFirst();
        mockMvc.perform(get("/posts/{user-id}/reading", firstUserId)
                        .param("search", "testTitle0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(lessThanOrEqualTo(1))))
                .andReturn();
    }

    @Test
    @DisplayName("최근 본 게시물 리스트 조회 - 페이징 테스트")
    void getRecentPostListWithPaging() throws Exception {
        Long firstUserId = userIds.getFirst();
        mockMvc.perform(get("/posts/{user-id}/reading", firstUserId)
                        .param("page", "2")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(lessThanOrEqualTo(5))))
                .andReturn();
    }

    @Test
    @DisplayName("최근 본 게시물 리스트 조회 - 정렬 테스트")
    void getRecentPostListSorted() throws Exception {
        Long firstUserId = userIds.getFirst();
        mockMvc.perform(get("/posts/{user-id}/reading", firstUserId)
                        .param("sort", "reg-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.posts[0].regDate", greaterThan("$.posts[1].regDate")))
                .andReturn();
    }
}