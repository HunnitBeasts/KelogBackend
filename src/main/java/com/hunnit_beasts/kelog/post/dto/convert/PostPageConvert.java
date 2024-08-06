package com.hunnit_beasts.kelog.post.dto.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostPageConvert {
    private Long postId;
    private String postThumbImage;
    private String title;
    private String shortContent;
    private LocalDateTime regDate;
    private Long commentCount;
    private String userThumbImage;
    private String nickname;
    private Long likeCount;
}
