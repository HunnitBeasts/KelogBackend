package com.hunnit_beasts.kelog.post.dto.convert;

import com.hunnit_beasts.kelog.post.enumeration.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostInfo {
    private final Long id;
    private final Long userId;
    private final String title;
    private final PostType type;
    private final String thumbImage;
    private final Boolean isPublic;
    private final String shortContent;
    private final String url;
    private final String content;
    private final LocalDateTime regDate;
    private final LocalDateTime modDate;
}
