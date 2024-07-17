package com.hunnit_beasts.kelog.user.dto.convert;

import com.hunnit_beasts.kelog.user.enumeration.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SocialInfos {
    private String url;
    private SocialType socialType;
}
