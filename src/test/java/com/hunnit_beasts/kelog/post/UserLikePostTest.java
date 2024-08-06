package com.hunnit_beasts.kelog.post;

import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
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
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class UserLikePostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostService postService;

    @Autowired
    private JwtUtil jwtUtil;

    private final List<Long> userIds = new ArrayList<>();
    private final List<Long> postIds = new ArrayList<>();
    private String token;

    @BeforeEach
    void setUp() {
        // Create 5 users
        for (int i = 0; i < 5; i++) {
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

        // Create token for the first user
        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userIds.getFirst())
                .userId("testUserId0")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        // Create 20 posts
        for (int i = 0; i < 20; i++) {
            Long authorId = userIds.get(i % 5);
            PostCreateRequestDTO postDto = PostCreateRequestDTO.builder()
                    .title("testTitle" + i)
                    .type(PostType.NORMAL)
                    .thumbImage("testThumbImage" + i)
                    .isPublic(true)
                    .shortContent("testShortContent" + i)
                    .url("testUrl" + i)
                    .content("testContent" + i)
                    .tags(List.of("tag" + (i % 3)))
                    .build();

            Long postId = postService.postCreate(authorId, postDto).getId();
            postIds.add(postId);

            // First user likes every other post
            if (i % 2 == 0) {
                PostLikeRequestDTO likeDto = PostLikeRequestDTO.builder()
                        .postId(postId)
                        .build();
                postService.addPostLike(userIds.get(0), likeDto);
            }
        }
    }

    @Test
    @DisplayName("사용자가 좋아요한 게시물 조회 성공")
    void getUserLikedPostsSuccess() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/like", userIds.getFirst())
                        .param("sort", "reg-date")
                        .param("page", "1")
                        .param("size", "10")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(10))
                .andExpect(jsonPath("$.posts", hasSize(10)))
                .andExpect(jsonPath("$.posts[0].title").value("testTitle18"))
                .andExpect(jsonPath("$.posts[0].postId").exists())
                .andExpect(jsonPath("$.posts[0].postThumbImage").exists())
                .andExpect(jsonPath("$.posts[0].shortContent").exists())
                .andExpect(jsonPath("$.posts[0].regDate").exists())
                .andExpect(jsonPath("$.posts[0].commentCount").exists())
                .andExpect(jsonPath("$.posts[0].userThumbImage").exists())
                .andExpect(jsonPath("$.posts[0].nickname").exists())
                .andExpect(jsonPath("$.posts[0].likeCount").exists());
    }

    @Test
    @DisplayName("사용자가 좋아요한 게시물 조회 - 정렬 기준: 작성일")
    void getUserLikedPostsSortedByRegDate() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/like", userIds.getFirst())
                        .param("sort", "reg-date")
                        .param("page", "1")
                        .param("size", "10")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts[0].title").value("testTitle18"));
    }

    @Test
    @DisplayName("사용자가 좋아요한 게시물 조회 - 검색 기능")
    void getUserLikedPostsWithSearch() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/like", userIds.getFirst())
                        .param("search", "testTitle1")
                        .param("page", "1")
                        .param("size", "10")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(5)))
                .andExpect(jsonPath("$.posts[*].title", everyItem(containsString("testTitle1"))));
    }

    @Test
    @DisplayName("사용자가 좋아요한 게시물 조회 - 페이지네이션")
    void getUserLikedPostsPagination() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/like", userIds.getFirst())
                        .param("page", "2")
                        .param("size", "5")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(5)))
                .andExpect(jsonPath("$.count").value(10));
    }

    @Test
    @DisplayName("권한 없는 사용자의 좋아요한 게시물 조회 시도")
    void getUserLikedPostsUnauthorized() throws Exception {
        String unauthorizedToken = "Bearer " + jwtUtil.createToken(CustomUserInfoDTO.builder()
                .id(userIds.get(1))
                .userId("testUserId1")
                .password("testPassword")
                .userType(UserType.USER)
                .build());

        mockMvc.perform(get("/posts/{user-id}/like", userIds.get(0))
                        .header("Authorization", unauthorizedToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("존재하지 않는 사용자의 좋아요한 게시물 조회 시도")
    void getNonExistentUserLikedPosts() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/like", 99999999L)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
