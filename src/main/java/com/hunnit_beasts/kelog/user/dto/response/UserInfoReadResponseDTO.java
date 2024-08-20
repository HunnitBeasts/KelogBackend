package com.hunnit_beasts.kelog.user.dto.response;

import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.convert.UserInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class UserInfoReadResponseDTO extends UserInfo {

    private final List<SocialInfos> socials;
    private final Boolean isFollow;

    public UserInfoReadResponseDTO(UserInfo userInfo, List<SocialInfos> socials) {
        super(userInfo.getNickname(),
                userInfo.getThumbImage(),
                userInfo.getBriefIntro(),
                userInfo.getKelogName(),
                userInfo.getFollowersCount(),
                userInfo.getFollowingsCount());

        this.socials = socials;
        this.isFollow = false;
    }

    public UserInfoReadResponseDTO(UserInfo userInfo, List<SocialInfos> socials, Boolean isFollow) {
        super(userInfo.getNickname(),
                userInfo.getThumbImage(),
                userInfo.getBriefIntro(),
                userInfo.getKelogName(),
                userInfo.getFollowersCount(),
                userInfo.getFollowingsCount());
        this.socials = socials;
        this.isFollow = isFollow;
    }
}
