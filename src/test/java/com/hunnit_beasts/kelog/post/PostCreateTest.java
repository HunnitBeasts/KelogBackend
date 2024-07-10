package com.hunnit_beasts.kelog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
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
class PostCreateTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    private Long userId;

    @BeforeEach
    void setUp(){
        UserCreateRequestDTO dto = UserCreateRequestDTO.builder()
                .userId("testUserId")
                .password("testPassword")
                .nickname("testNickname")
                .briefIntro("testBriefIntro")
                .email("testEmail")
                .build();

        userId = authService.signUp(dto).getId();
    }

    @Test
    @DisplayName("게시물 작성 테스트")
    void createPost() throws Exception {
        PostCreateRequestDTO dto = PostCreateRequestDTO.builder()
                .title("testTitle")
                .type(PostType.NORMAL)
                .thumbImage("testThumbImage")
                .isPublic(Boolean.TRUE)
                .shortContent("testShortContent")
                .url("testUrl")
                .content("testContent")
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        String token = jwtUtil.createToken(userInfoDTO);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("userId").isNumber())
                .andExpect(jsonPath(".title").value("testTitle"))
                .andExpect(jsonPath(".type").value("NORMAL"))
                .andExpect(jsonPath(".thumbImage").value("testThumbImage"))
                .andExpect(jsonPath("isPublic").value("true"))
                .andExpect(jsonPath(".shortContent").value("testShortContent"))
                .andExpect(jsonPath(".url").value("testUrl"))
                .andExpect(jsonPath(".content").value("testContent"))
                .andExpect(jsonPath("regDate").isString())
                .andExpect(jsonPath("modDate").isString())
                .andReturn();
    }
}
