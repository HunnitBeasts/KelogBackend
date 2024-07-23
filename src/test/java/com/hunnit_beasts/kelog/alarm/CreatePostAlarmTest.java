package com.hunnit_beasts.kelog.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.repository.AlarmJpaRepository;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
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

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CreatePostAlarmTest {
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

    @Autowired
    AlarmJpaRepository alarmJpaRepository;

    private Long userId;
    private String token;
    private Long followUserId;
    private String followToken;

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

        //포스트 알람을 위한 팔로우유저, user(userId 가진) 를 팔로우함
        UserCreateRequestDTO followUserDTO = UserCreateRequestDTO.builder()
                .userId("testUserId1")
                .password("testPassword1")
                .nickname("testNickname1")
                .briefIntro("testBriefIntro1")
                .email("testEmail1")
                .build();

        followUserId = authService.signUp(followUserDTO).getId();

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(this.userId)
                .userId("testUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

        CustomUserInfoDTO followUserInfoDTO = CustomUserInfoDTO.builder()
                .id(this.followUserId)
                .userId("followUserId")
                .password("testPassword")
                .userType(UserType.USER)
                .build();

        followToken = "Bearer " + jwtUtil.createToken(followUserInfoDTO);

    }
    @Test
    @DisplayName("게시물 알람 테스트")
    void tagCreatePostAlarm() throws Exception {

        //먼저 팔로잉
        FollowIngRequestDTO followIngRequestDTO = FollowIngRequestDTO.builder()
                .followee(userId)
                .build();

        String followJsonContent = objectMapper.writeValueAsString(followIngRequestDTO);

        mockMvc.perform(post("/following")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", followToken)
                        .content(followJsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.follower").value(followUserId))
                .andExpect(jsonPath("$.followee").value(userId))
                .andReturn();

        //그다음 포스트 생성

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

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.title").value("testTitle"))
                .andExpect(jsonPath("$.type").value("NORMAL"))
                .andExpect(jsonPath("$.thumbImage").value("testThumbImage"))
                .andExpect(jsonPath("$.isPublic").value("true"))
                .andExpect(jsonPath("$.shortContent").value("testShortContent"))
                .andExpect(jsonPath("$.url").value("testUrl"))
                .andExpect(jsonPath("$.content").value("testContent"))
                .andExpect(jsonPath("$.regDate").isString())
                .andExpect(jsonPath("$.modDate").isString())
                .andReturn();

        //다음 알람 확인

        Assertions
                .assertThat(alarmJpaRepository.existsByUser_IdAndTarget_IdAndAlarmType(userId,followUserId, AlarmType.SUBSCRIBE))
                .isTrue();
    }
}
