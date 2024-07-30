package com.hunnit_beasts.kelog.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.auth.jwt.JwtUtil;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.repository.jpa.AlarmJpaRepository;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CreateCommentAlarmTest {
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
    private Long commentWriterId;
    private Long postId;
    private String token;
    private String commentWriterToken;

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

        PostCreateRequestDTO postDto = PostCreateRequestDTO.builder()
                .title("testTitle")
                .type(PostType.NORMAL)
                .thumbImage("testThumbImage")
                .isPublic(Boolean.TRUE)
                .shortContent("testShortContent")
                .url("testUrl")
                .content("testContent")
                .build();

        postId = postService.postCreate(userId, postDto).getId();

        UserCreateRequestDTO commentWriter = UserCreateRequestDTO.builder()
                .userId("testCommentWriterId")
                .password("testCommentWriterPassword")
                .nickname("testCommentWriterNickname")
                .briefIntro("testBriefIntro")
                .email("testCommentWriterEmail")
                .build();

        commentWriterId = authService.signUp(commentWriter).getId();

        CustomUserInfoDTO commentUserInfoDTO = CustomUserInfoDTO.builder()
                .id(this.commentWriterId)
                .userId("testCommentWriterId")
                .password("testCommentWriterPassword")
                .userType(UserType.USER)
                .build();

        commentWriterToken = "Bearer " + jwtUtil.createToken(commentUserInfoDTO);

        CustomUserInfoDTO userInfoDTO = CustomUserInfoDTO.builder()
                .id(this.userId)
                .userId("testCommentId")
                .password("testCommentPassword")
                .userType(UserType.USER)
                .build();

        token = "Bearer " + jwtUtil.createToken(userInfoDTO);

    }
    @Test
    @DisplayName("댓글알람 테스트(본인게시물에 본인이 댓글 쓴 경우)")
    void createCommentAlarm1() throws Exception {
        //본인 게시물에 댓글 단 경우 알람 오면안됨
        CommentCreateRequestDTO dto = CommentCreateRequestDTO.builder()
                .postId(postId)
                .content("testCommentContent")
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        CommentCreateResponseDTO commentDto = objectMapper.readValue(result.getResponse().getContentAsString(),CommentCreateResponseDTO.class);
        Long commentId = commentDto.getId();

        Assertions
                .assertThat(alarmJpaRepository.existsByUser_IdAndTargetIdAndAlarmType(userId,commentId, AlarmType.COMMENT))
                .isFalse();

    }

    @Test
    @DisplayName("댓글알람 테스트(본인게시물에 다른사람이 댓글 쓴 경우)")
    void createCommentAlarm2() throws Exception {
        //알람 와야 정상
        CommentCreateRequestDTO dto = CommentCreateRequestDTO.builder()
                .postId(postId)
                .content("testCommentContent")
                .build();

        String jsonContent = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", commentWriterToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        CommentCreateResponseDTO commentDto = objectMapper.readValue(result.getResponse().getContentAsString(),CommentCreateResponseDTO.class);
        Long commentId = commentDto.getId();

        Assertions
                .assertThat(alarmJpaRepository.existsByUser_IdAndTargetIdAndAlarmType(userId,commentId, AlarmType.COMMENT))
                .isTrue();

    }

}
