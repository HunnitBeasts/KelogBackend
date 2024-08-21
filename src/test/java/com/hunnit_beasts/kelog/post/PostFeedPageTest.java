package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.JsonNode;
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
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import com.hunnit_beasts.kelog.user.service.UserService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class PostFeedPageTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ObjectMapper objectMapper;

    private final List<Long> userIds = new ArrayList<>();
    private final List<Long> postIds = new ArrayList<>();
    String token;
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

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userIds.getFirst())
                .userId("testUserId0")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        // Create follow relationships
        for (int i = 1; i < 5; i++) {
            FollowIngRequestDTO followDto = FollowIngRequestDTO.builder()
                    .followee(userIds.get(i))
                    .build();
            userService.following(userIds.getFirst(), followDto);
        }

        // Create posts
        for (int i = 0; i < 50; i++) {
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
            // Add likes
            for (int j = 1; j < userIds.size(); j += 2) {
                Long likerId = userIds.get(j);
                PostLikeRequestDTO likeDto = PostLikeRequestDTO.builder()
                        .postId(postId)
                        .build();
                postService.addPostLike(likerId, likeDto);
            }

            // Add comments
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
    @DisplayName("피드 게시물 조회 테스트")
    void getFeedPosts() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/feed", userIds.getFirst())
                        .param("sort", "reg-date")
                        .param("page", "1")
                        .param("size", "20")
                        .param("search", "testTitle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.count").value(greaterThan(0)))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.posts", hasSize(lessThanOrEqualTo(20))))
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
                .andExpect(jsonPath("$.posts[*].userId", everyItem(is(in(userIds.subList(1, 5).stream().map(Long::intValue).toList())))));
    }

    @Test
    @DisplayName("피드 게시물 조회 - 검색 테스트")
    void getFeedPostsWithSearch() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/feed", userIds.getFirst())
                        .param("search", "testTitle0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(lessThanOrEqualTo(1))))
                .andReturn();
    }

    @Test
    @DisplayName("피드 게시물 조회 - 페이징 테스트")
    void getFeedPostsWithPaging() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/feed", userIds.getFirst())
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(lessThanOrEqualTo(10))))
                .andReturn();
    }

    @Test
    @DisplayName("피드 게시물 조회 - 정렬 테스트 (등록일 내림차순)")
    void getFeedPostsSortedByRegDate() throws Exception {
        mockMvc.perform(get("/posts/{user-id}/feed", userIds.getFirst())
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
