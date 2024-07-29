package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.dto.response.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.postassist.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.postassist.dto.request.SeriesUpdateRequestDTO;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class SeriesUpdateTest {

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
    private String token;
    private final List<Long> postIds = new ArrayList<>();

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

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(id)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        SeriesCreateRequestDTO seriesDto = SeriesCreateRequestDTO.builder()
                .seriesName("테스트 시리즈 이름")
                .url("testSeriesUrl")
                .build();

        seriesId = seriesService.createSeries(id,seriesDto).getId();

        for (int i = 0; i < 5; i++) {
            PostCreateRequestDTO fPostDto = PostCreateRequestDTO.builder()
                    .title("testTitle : " + i)
                    .type(PostType.NORMAL)
                    .thumbImage("testThumbImage")
                    .isPublic(Boolean.TRUE)
                    .shortContent("testShortContent")
                    .url("testUrl : " + i)
                    .content("testContent")
                    .build();

            Long postId = postService.postCreate(id, fPostDto).getId();
            postIds.add(postId);

            seriesService.seriesAddPost(postId,seriesId);
        }
    }

    @Test
    @DisplayName("시리즈 업데이트")
    void updateSeries() throws Exception {
        String newSeriesName = "업데이트된 시리즈 이름";
        List<Long> newPostOrder = Arrays.asList(postIds.get(4), postIds.get(3), postIds.get(2), postIds.get(1), postIds.get(0));

        SeriesUpdateRequestDTO updateDto = new SeriesUpdateRequestDTO(newSeriesName, newPostOrder);

        mockMvc.perform(patch("/series/{series-id}", seriesId)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seriesName").value(newSeriesName))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts", hasSize(5)))
                .andExpect(jsonPath("$.posts[0].title").value("testTitle : 4"))
                .andExpect(jsonPath("$.posts[0].postUrl").value("testUrl : 4"))
                .andExpect(jsonPath("$.posts[0].userId").value(userId))
                .andExpect(jsonPath("$.posts[0].postId").value(postIds.get(4)))
                .andExpect(jsonPath("$.posts[1].title").value("testTitle : 3"))
                .andExpect(jsonPath("$.posts[1].postUrl").value("testUrl : 3"))
                .andExpect(jsonPath("$.posts[1].userId").value(userId))
                .andExpect(jsonPath("$.posts[1].postId").value(postIds.get(3)))
                .andExpect(jsonPath("$.posts[4].title").value("testTitle : 0"))
                .andExpect(jsonPath("$.posts[4].postUrl").value("testUrl : 0"))
                .andExpect(jsonPath("$.posts[4].userId").value(userId))
                .andExpect(jsonPath("$.posts[4].postId").value(postIds.get(0)))
                .andReturn();
    }
}
