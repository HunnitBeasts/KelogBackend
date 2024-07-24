package com.hunnit_beasts.kelog.user.dto.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowerInfos {
    private String thumbnail;
    private String nickname;
    private String userId;
    private String briefIntro;
    private Boolean isFollow;
}
