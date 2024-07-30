package com.hunnit_beasts.kelog.alarm;

import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.repository.jpa.AlarmJpaRepository;
import com.hunnit_beasts.kelog.common.service.AlarmService;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostLikeResponseDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.post.service.PostService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlarmAsyncTest {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private AlarmJpaRepository alarmJpaRepository;

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    private Long userId;
    private Long postId;
    @BeforeEach
    void setup(){
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

        postId = postService.postCreate(userId,postDto).getId();

    }

    @Test
    @DisplayName("알람 비동기 테스트")
    void testAlarmAsync() throws Exception {

//        PostLikeResponseDTO dto = new PostLikeResponseDTO(userId,postId);
//
//
//        CompletableFuture<Void> future = alarmService.newLikeAlarm(dto);
//        assertFalse(future.isDone(), "Async method should not be done yet");
//        future.join();
//        assertTrue(future.isDone(), "Async method should be done");
    }
}
