package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.post.dto.request.PostAddRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import com.hunnit_beasts.kelog.user.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.user.jwt.JwtUtil;
import com.hunnit_beasts.kelog.user.service.AuthService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KelogApplication.class)
@Transactional
@AutoConfigureMockMvc
class AddPostTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    private Long userId;
    private Long seriesId;
    private String token;
    private Long firstPostId;
    private Long secondPostId;
    private Long eUserId;
    private Long ePostId;
    private Long eSeriesId;
    private String eToken;

    @BeforeEach
    void setUp(){
        UserCreateRequestDTO userDto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        userId = authService.signUp(userDto).getId();

        SeriesCreateRequestDTO seriesDto = SeriesCreateRequestDTO.builder()
                .seriesName("테스트 시리즈 이름")
                .url("testSeriesUrl")
                .build();

        seriesId = postService.createSeries(userId,seriesDto).getId();

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

        PostCreateRequestDTO sPostDto = PostCreateRequestDTO.builder()
                .title("testTitle")
                .type(PostType.NORMAL)
                .thumbImage("testThumbImage")
                .isPublic(Boolean.TRUE)
                .shortContent("testShortContent")
                .url("testUrl")
                .content("testContent")
                .build();

        secondPostId = postService.postCreate(userId, sPostDto).getId();

        UserCreateRequestDTO eUserDto = UserCreateRequestDTO.builder()
                .userId("testUserId1")
                .password("testPassword1")
                .nickname("testNickname1")
                .briefIntro("testBriefIntro1")
                .email("testEmail1")
                .build();

        eUserId = authService.signUp(eUserDto).getId();

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

        eSeriesId = postService.createSeries(eUserId,eSeriesDto).getId();

    }

    @Test
    @DisplayName("시리즈에 포스트 추가")
    void seriesAddPost() throws Exception {
        PostAddRequestDTO dto = PostAddRequestDTO.builder()
                .postId(firstPostId)
                .seriesId(seriesId)
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/series/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(firstPostId))
                .andExpect(jsonPath("$.seriesId").value(seriesId))
                .andExpect(jsonPath("$.seriesOrder").value(1))
                .andReturn();
    }

    @Test
    @DisplayName("중복 포스트")
    void seriesAddPostDuple() throws Exception {
        PostAddRequestDTO dto = PostAddRequestDTO.builder()
                .postId(firstPostId)
                .seriesId(seriesId)
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/series/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(firstPostId))
                .andExpect(jsonPath("$.seriesId").value(seriesId))
                .andExpect(jsonPath("$.seriesOrder").value(1))
                .andReturn();

        mockMvc.perform(post("/series/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("시리즈에 포스트 두개 추가")
    void seriesAddTwoPost() throws Exception {
        PostAddRequestDTO dto = PostAddRequestDTO.builder()
                .postId(firstPostId)
                .seriesId(seriesId)
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/series/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(firstPostId))
                .andExpect(jsonPath("$.seriesId").value(seriesId))
                .andExpect(jsonPath("$.seriesOrder").value(1))
                .andReturn();

        PostAddRequestDTO dto2 = PostAddRequestDTO.builder()
                .postId(secondPostId)
                .seriesId(seriesId)
                .build();

        String jsonContent2 = objectMapper.writeValueAsString(dto2);

        mockMvc.perform(post("/series/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(secondPostId))
                .andExpect(jsonPath("$.seriesId").value(seriesId))
                .andExpect(jsonPath("$.seriesOrder").value(2))
                .andReturn();
    }

    @Test
    @DisplayName("본인 시리즈 아닐 때시리즈에 포스트 추가")
    void notMySeriesAddPost() throws Exception {
        PostAddRequestDTO dto = PostAddRequestDTO.builder()
                .postId(firstPostId)
                .seriesId(eSeriesId)
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/series/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("본인 포스트 아닐 때시리즈에 포스트 추가")
    void seriesAddNotMyPost() throws Exception {
        PostAddRequestDTO dto = PostAddRequestDTO.builder()
                .postId(eSeriesId)
                .seriesId(seriesId)
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/series/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}
