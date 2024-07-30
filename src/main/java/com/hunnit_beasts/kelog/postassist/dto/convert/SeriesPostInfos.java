package com.hunnit_beasts.kelog.postassist.dto.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SeriesPostInfos {
    private Long postId;
    private String userId;
    private String postUrl;
    private String thumbnail;
    private String title;
    private LocalDateTime regDate;
    private String content;
}
