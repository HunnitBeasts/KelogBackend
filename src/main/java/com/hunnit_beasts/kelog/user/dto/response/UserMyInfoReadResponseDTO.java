package com.hunnit_beasts.kelog.user.dto.response;

import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.convert.UserMyInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class UserMyInfoReadResponseDTO extends UserMyInfo {
    private List<SocialInfos> socials;

    public UserMyInfoReadResponseDTO(UserMyInfo myInfo, List<SocialInfos> socials) {
        super(myInfo.getNickname(),
                myInfo.getThumbImage(),
                myInfo.getBriefIntro(),
                myInfo.getIntro(),
                myInfo.getEmail(),
                myInfo.getEmailSetting(),
                myInfo.getAlarmSetting(),
                myInfo.getKelogName(),
                myInfo.getAlarmCount());
        this.socials = socials;
    }

}
