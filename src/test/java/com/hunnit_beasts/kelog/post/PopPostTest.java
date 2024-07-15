package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.postassist.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.service.SeriesService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class PopPostTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @Autowired
    SeriesService seriesService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    private Long seriesId;
    private String token;
    private Long firstPostId;
    private Long ePostId;
    private Long eSeriesId;

    @BeforeEach
    void setUp(){
        UserCreateRequestDTO userDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        Long userId = authService.signUp(userDto).getId();

        SeriesCreateRequestDTO seriesDto = SeriesCreateRequestDTO.builder()
                .seriesName("테스트 시리즈 이름")
                .url("testSeriesUrl")
                .build();

        seriesId = seriesService.createSeries(userId,seriesDto).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        PostCreateRequestDTO fPostDto = PostCreateRequestDTO.builder()
                .title("testTitle")
                .type(PostType.NORMAL)
                .thumbImage("testThumbImage")
                .isPublic(Boolean.TRUE)
                .shortContent("testShortContent")
                .url("testUrl")
                .content("testContent")
                .build();

        firstPostId = postService.postCreate(userId, fPostDto).getId();

        UserCreateRequestDTO eUserDto = UserCreateRequestDTO.builder()
                .userId("testUserId1")
                .password("testPassword1")
                .nickname("testNickname1")
                .briefIntro("testBriefIntro1")
                .email("testEmail1")
                .build();

        Long eUserId = authService.signUp(eUserDto).getId();

        PostCreateRequestDTO ePostDto = PostCreateRequestDTO.builder()
                .title("testTitle")
                .type(PostType.NORMAL)
                .thumbImage("testThumbImage")
                .isPublic(Boolean.TRUE)
                .shortContent("testShortContent")
                .url("testUrl")
                .content("testContent")
                .build();

        ePostId = postService.postCreate(eUserId, ePostDto).getId();

        SeriesCreateRequestDTO eSeriesDto = SeriesCreateRequestDTO.builder()
                .seriesName("테스트 시리즈 이름")
                .url("testSeriesUrl")
                .build();

        eSeriesId = seriesService.createSeries(eUserId,eSeriesDto).getId();

        seriesService.seriesAddPost(firstPostId,seriesId);

    }

    @Test
    @DisplayName("시리즈에 있는 포스트 삭제 추가")
    void seriesPopPost() throws Exception {
        mockMvc.perform(delete("/series/post/{series-id}/{post-id}",seriesId, firstPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.postId").value(firstPostId))
                .andExpect(jsonPath("$.seriesId").value(seriesId))
                .andReturn();
    }

    @Test
    @DisplayName("없는 상태서 또 삭제")
    void seriesAddPostDuple() throws Exception {
        mockMvc.perform(delete("/series/post/{series-id}/{post-id}",seriesId, firstPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.postId").value(firstPostId))
                .andExpect(jsonPath("$.seriesId").value(seriesId))
                .andReturn();

        mockMvc.perform(delete("/series/post/{series-id}/{post-id}",seriesId, firstPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("시리즈 본인꺼 아님")
    void notMySeriesPopPost() throws Exception {
        mockMvc.perform(delete("/series/post/{series-id}/{post-id}",eSeriesId, firstPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("포스트 본인꺼 아님")
    void seriesPopNotMyPost() throws Exception {
        mockMvc.perform(delete("/series/post/{series-id}/{post-id}",seriesId, ePostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}
