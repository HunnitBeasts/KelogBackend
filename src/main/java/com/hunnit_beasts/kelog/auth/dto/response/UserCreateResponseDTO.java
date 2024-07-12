package com.hunnit_beasts.kelog.auth.dto.response;

import com.hunnit_beasts.kelog.user.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserCreateResponseDTO {
    private Long id;
    private String userId;
    private String nickname;
    private String thumbImage;
    private String briefIntro;
    private String email;
    private Boolean emailSetting;
    private Boolean alarmSetting;
    private UserType userType;
    private String kelogName;
    private String userIntro;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
