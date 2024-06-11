package com.hunnit_beasts.kelog.dto.response.user;

import com.hunnit_beasts.kelog.enumeration.types.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
