package com.hunnit_beasts.kelog.user.dto.request;

import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoUpdateRequestDTO {
    private String nickname;
    private String thumbImage;
    private String briefIntro;
    private String email;
    private Boolean emailSetting;
    private Boolean alarmSetting;
    private String kelogName;
    private List<SocialInfos> soicals;

}
