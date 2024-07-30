package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.dto.response.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.postassist.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.service.SeriesService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class SeriesPostTest {

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

    private String userId;
    private Long seriesId;
    private Long postId;

    @BeforeEach
    void setUp(){
        UserCreateRequestDTO userDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        UserCreateResponseDTO userCreateResponseDTO = authService.signUp(userDto);
        userId = userCreateResponseDTO.getUserId();
        Long id = userCreateResponseDTO.getId();

        SeriesCreateRequestDTO seriesDto = SeriesCreateRequestDTO.builder()
                .seriesName("테스트 시리즈 이름")
                .url("testSeriesUrl")
                .build();

        seriesId = seriesService.createSeries(id,seriesDto).getId();

        for (int i = 0; i < 5; i++) {
            PostCreateRequestDTO fPostDto = PostCreateRequestDTO.builder()
                    .title("testTitle : " + i)
                    .type(PostType.NORMAL)
                    .thumbImage("testThumbImage : " + i)
                    .isPublic(Boolean.TRUE)
                    .shortContent("testShortContent : " + i)
                    .url("testUrl : " + i)
                    .content("testContent")
                    .build();

            postId = postService.postCreate(id, fPostDto).getId();

            seriesService.seriesAddPost(postId,seriesId);
        }
    }

    @Test
    @DisplayName("시리즈 검색")
    void findSeries() throws Exception {
        mockMvc.perform(get("/series/{series-id}",seriesId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seriesName").value("테스트 시리즈 이름"))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts", hasSize(5)))
                .andExpect(jsonPath("$.posts[0].title").value("testTitle : 0"))
                .andExpect(jsonPath("$.posts[0].postUrl").value("testUrl : 0"))
                .andExpect(jsonPath("$.posts[0].userId").value(userId))
                .andExpect(jsonPath("$.posts[0].postId").isNumber())
                .andExpect(jsonPath("$.posts[0].thumbnail").value("testThumbImage : 0"))
                .andExpect(jsonPath("$.posts[0].regDate").isString())
                .andExpect(jsonPath("$.posts[0].content").value("testShortContent : 0"))
                .andExpect(jsonPath("$.posts[1].title").value("testTitle : 1"))
                .andExpect(jsonPath("$.posts[1].postUrl").value("testUrl : 1"))
                .andExpect(jsonPath("$.posts[1].userId").value(userId))
                .andExpect(jsonPath("$.posts[1].postId").isNumber())
                .andExpect(jsonPath("$.posts[1].thumbnail").value("testThumbImage : 1"))
                .andExpect(jsonPath("$.posts[1].regDate").isString())
                .andExpect(jsonPath("$.posts[1].content").value("testShortContent : 1"))
                .andExpect(jsonPath("$.posts[2].title").value("testTitle : 2"))
                .andExpect(jsonPath("$.posts[2].postUrl").value("testUrl : 2"))
                .andExpect(jsonPath("$.posts[2].userId").value(userId))
                .andExpect(jsonPath("$.posts[2].postId").isNumber())
                .andExpect(jsonPath("$.posts[2].thumbnail").value("testThumbImage : 2"))
                .andExpect(jsonPath("$.posts[2].regDate").isString())
                .andExpect(jsonPath("$.posts[2].content").value("testShortContent : 2"))
                .andReturn();
    }

}
