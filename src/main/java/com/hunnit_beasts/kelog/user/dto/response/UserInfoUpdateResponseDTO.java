package com.hunnit_beasts.kelog.user.dto.response;

import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.convert.UserUpdateInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class UserInfoUpdateResponseDTO extends UserUpdateInfo {
    private final List<SocialInfos> socials;

    public UserInfoUpdateResponseDTO(UserUpdateInfo userUpdateInfo, List<SocialInfos> socials) {
        super(userUpdateInfo.getNickname(),
                userUpdateInfo.getThumbImage(),
                userUpdateInfo.getBriefIntro(),
                userUpdateInfo.getEmail(),
                userUpdateInfo.getEmailSetting(),
                userUpdateInfo.getAlarmSetting(),
                userUpdateInfo.getKelogName());
        this.socials = socials;
    }
}
