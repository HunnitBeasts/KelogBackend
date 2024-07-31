package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.comment.service.CommentService;
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
class PostListTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    private final List<Long> userIds = new ArrayList<>();
    private final List<Long> postIds = new ArrayList<>();
    private String token;
    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        // Create 10 users
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

        // Create token for the first user
        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userIds.getFirst())
                .userId("testUserId0")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        // Create 100 posts with random authors
        for (int i = 0; i < 20; i++) {
            Long authorId = userIds.get(random.nextInt(userIds.size()));
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

            // Add likes from odd-indexed users
            for (int j = 1; j < userIds.size(); j += 2) {
                Long likerId = userIds.get(j);
                PostLikeRequestDTO likeDto = PostLikeRequestDTO.builder()
                        .postId(postId)
                        .build();
                postService.addPostLike(likerId, likeDto);
            }

            // Add random number of comments (0-3)
            int commentsCount = random.nextInt(4);
            for (int j = 0; j < commentsCount; j++) {
                Long commenterId = userIds.get(random.nextInt(userIds.size()));
                CommentCreateRequestDTO commentDto = CommentCreateRequestDTO.builder()
                        .postId(postId)
                        .content("testCommentContent" + j)
                        .build();
                commentService.commentCreate(commenterId, commentDto);
            }
        }
    }

    @Test
    @DisplayName("게시물 리스트 조회 테스트")
    void getPostList() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("tag-name", "tag1")
                        .param("sort", "reg-date")
                        .param("page", "1")
                        .param("size", "20")
                        .param("search", "testTitle")
                        .param("user-id", userIds.getFirst().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts", hasSize(lessThanOrEqualTo(20))))
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
    @DisplayName("게시물 리스트 조회 - 태그 필터링 테스트")
    void getPostListWithTagFilter() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("tag-name", "tag0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(lessThanOrEqualTo(20))))
                .andReturn();
    }

    @Test
    @DisplayName("게시물 리스트 조회 - 검색 테스트")
    void getPostListWithSearch() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("search", "testTitle0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(1)))
                .andReturn();
    }

    @Test
    @DisplayName("게시물 리스트 조회 - 페이징 테스트")
    void getPostListWithPaging() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", "1")
                        .param("size", "30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(30)))
                .andReturn();
    }

    @Test
    @DisplayName("게시물 리스트 조회 - 사용자별 게시물 테스트")
    void getPostListByUser() throws Exception {
        Long testUserId = userIds.get(0);
        mockMvc.perform(get("/posts")
                        .param("user-id", testUserId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts[*].userId", everyItem(is(testUserId.intValue()))))
                .andReturn();
    }

    @Test
    @DisplayName("게시물 리스트 조회 - 정렬 테스트 (좋아요 수)")
    void getPostListSortedByLikes() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("sort", "likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.posts[0].likeCount", is(5))) // 모든 게시물의 좋아요 수는 5개로 동일합니다.
                .andReturn();
    }
}
