package com.hunnit_beasts.kelog.user.dto.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {
    private final String nickname;
    private final String thumbImage;
    private final String briefIntro;
    private final String kelogName;
    private final Long followersCount;
    private final Long followingsCount;
}
