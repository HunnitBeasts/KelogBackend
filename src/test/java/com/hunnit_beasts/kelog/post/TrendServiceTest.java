package com.hunnit_beasts.kelog.post;

import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.comment.service.CommentService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.enumeration.TrendType;
import com.hunnit_beasts.kelog.post.repository.querydsl.PostListQueryDSLRepository;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.post.service.TrendCachingService;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class TrendServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TrendCachingService trendCachingService;

    @Autowired
    private PostListQueryDSLRepository postListQueryDSLRepository;

    private final List<Long> userIds = new ArrayList<>();
    private final List<Long> postIds = new ArrayList<>();
    private final Map<Long, Set<Long>> postLikes = new HashMap<>();
    private String token;
    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 20; i++) {
            UserCreateRequestDTO dto = UserCreateRequestDTO.builder()
                    .userId("testUserId" + i)
                    .password("testPassword")
                    .nickname("testNickname" + i)
                    .briefIntro("testBriefIntro")
                    .email("testEmail" + i + "@test.com")
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
            postLikes.put(postId, new HashSet<>());

            addRandomInteractions(postId);
        }

        trendCachingService.updateTrendCache();
    }

    private void addRandomInteractions(Long postId) {
        int likesCount = random.nextInt(10);
        for (int j = 0; j < likesCount; j++) {
            Long likerId = getRandomUniqueLikerId(postLikes.get(postId));
            if (likerId != null) {
                PostLikeRequestDTO likeDto = PostLikeRequestDTO.builder()
                        .postId(postId)
                        .build();
                postService.addPostLike(likerId, likeDto);
                postLikes.get(postId).add(likerId);
            }
        }

        int commentsCount = random.nextInt(5);
        for (int j = 0; j < commentsCount; j++) {
            Long commenterId = userIds.get(random.nextInt(userIds.size()));
            CommentCreateRequestDTO commentDto = CommentCreateRequestDTO.builder()
                    .postId(postId)
                    .content("testCommentContent" + j)
                    .build();
            commentService.commentCreate(commenterId, commentDto);
        }
    }

    private Long getRandomUniqueLikerId(Set<Long> existingLikes) {
        List<Long> availableUsers = new ArrayList<>(userIds);
        availableUsers.removeAll(existingLikes);
        if (availableUsers.isEmpty()) {
            return null;
        }
        return availableUsers.get(random.nextInt(availableUsers.size()));
    }

    @Test
    @DisplayName("트렌드 게시물 캐싱 및 조회 테스트")
    void testTrendPostCachingAndRetrieval() throws Exception {
        for (TrendType trendType : TrendType.values()) {
            mockMvc.perform(get("/posts/trending/{trend-type}", trendType)
                            .param("sort", "score")
                            .param("page", "1")
                            .param("size", "20")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.posts", hasSize(lessThanOrEqualTo(20))))
                    .andExpect(jsonPath("$.count", is(30)));
        }
    }

    @Test
    @DisplayName("트렌드 게시물 페이징 테스트")
    void testTrendPostPagination() throws Exception {
        mockMvc.perform(get("/posts/trending/{trend-type}", TrendType.DAY)
                        .param("sort", "score")
                        .param("page", "2")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(lessThanOrEqualTo(10))))
                .andExpect(jsonPath("$.count", is(30)));
    }

    @Test
    @DisplayName("트렌드 게시물 검색 테스트")
    void testTrendPostSearch() throws Exception {
        mockMvc.perform(get("/posts/trending/{trend-type}", TrendType.WEEK)
                        .param("sort", "score")
                        .param("page", "1")
                        .param("size", "20")
                        .param("search", "testTitle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts[*].title", everyItem(containsString("testTitle"))));
    }

    @Test
    @DisplayName("트렌드 게시물 캐시 갱신 테스트")
    void testTrendPostCacheUpdate() throws Exception {
        Long trendyPostId = postIds.getFirst();
        for (int i = 0; i < 20; i++) {
            commentService.commentCreate(userIds.get(i % userIds.size()), new CommentCreateRequestDTO(trendyPostId, "Trendy comment"));
        }

        trendCachingService.updateTrendCache();

        mockMvc.perform(get("/posts/trending/{trend-type}", TrendType.DAY)
                        .param("sort", "score")
                        .param("page", "1")
                        .param("size", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts[0].postId", is(trendyPostId.intValue())));
    }

    @Test
    @DisplayName("캐시가 없을 때 트렌드 게시물 조회 테스트")
    void testGetTrendPostsWithoutCache() throws Exception {
        clearCache();

        assertNull(getCachedTrendPosts(TrendType.DAY));

        mockMvc.perform(get("/posts/trending/{trend-type}", TrendType.DAY)
                        .param("sort", "score")
                        .param("page", "1")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts").exists())
                .andExpect(jsonPath("$.count").exists());

        assertNotNull(getCachedTrendPosts(TrendType.DAY));
    }

    private void clearCache() {
        Cache cache = cacheManager.getCache("trendPosts");
        if (cache != null)
            cache.clear();
    }

    private PostPageResponseDTO getCachedTrendPosts(TrendType trendType) {
        Cache cache = cacheManager.getCache("trendPosts");
        return cache != null ? cache.get(trendType.name(), PostPageResponseDTO.class) : null;
    }
}
