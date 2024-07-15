package com.hunnit_beasts.kelog.post.dto.convert;

import com.hunnit_beasts.kelog.post.enumeration.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostInfos {
    private Long id;
    private Long userId;
    private String title;
    private PostType type;
    private String thumbImage;
    private Boolean isPublic;
    private String shortContent;
    private String url;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
