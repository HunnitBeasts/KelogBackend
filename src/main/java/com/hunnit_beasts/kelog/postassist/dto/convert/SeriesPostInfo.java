package com.hunnit_beasts.kelog.postassist.dto.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeriesPostInfo {
    private Long postId;
    private String userId;
    private String postUrl;
    private String title;
}
