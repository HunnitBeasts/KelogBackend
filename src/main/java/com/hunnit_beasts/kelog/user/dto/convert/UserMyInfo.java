package com.hunnit_beasts.kelog.user.dto.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMyInfo {
    private final String nickname;
    private final String thumbImage;
    private final String briefIntro;
    private final String intro;
    private final String email;
    private final Boolean emailSetting;
    private final Boolean alarmSetting;
    private final String kelogName;
    private final Long alarmCount;
}
