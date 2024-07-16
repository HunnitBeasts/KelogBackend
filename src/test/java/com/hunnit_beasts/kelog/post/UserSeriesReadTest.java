package com.hunnit_beasts.kelog.post;

import com.hunnit_beasts.kelog.KelogApplication;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.service.AuthService;
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
class UserSeriesReadTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    SeriesService seriesService;

    private Long userId;

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

        for (int i = 0; i < 7; i++) {
            SeriesCreateRequestDTO seriesDto = SeriesCreateRequestDTO.builder()
                    .seriesName("테스트 시리즈 이름 : " + i)
                    .url("testSeriesUrl : " + i)
                    .build();
            seriesService.createSeries(userId,seriesDto);
        }
    }

    @Test
    @DisplayName("유저별 시리즈 읽기")
    void userSeriesRead() throws Exception {
        mockMvc.perform(get("/series/{user-id}/users",userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seriesList").isArray())
                .andExpect(jsonPath("$.seriesList", hasSize(7)))
                .andExpect(jsonPath("$.seriesList[0].id").isNumber())
                .andExpect(jsonPath("$.seriesList[0].url").value("testSeriesUrl : 0"))
                .andExpect(jsonPath("$.seriesList[0].name").value("테스트 시리즈 이름 : 0"))
                .andExpect(jsonPath("$.seriesList[1].id").isNumber())
                .andExpect(jsonPath("$.seriesList[1].url").value("testSeriesUrl : 1"))
                .andExpect(jsonPath("$.seriesList[1].name").value("테스트 시리즈 이름 : 1"))
                .andExpect(jsonPath("$.seriesList[2].id").isNumber())
                .andExpect(jsonPath("$.seriesList[2].url").value("testSeriesUrl : 2"))
                .andExpect(jsonPath("$.seriesList[2].name").value("테스트 시리즈 이름 : 2"))
                .andReturn();
    }
}
