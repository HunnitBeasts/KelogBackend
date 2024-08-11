package com.hunnit_beasts.kelog.comment.dto.convert;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class CommentListInfo {
    private Long id;
    private String thumbImage;
    private String nickname;
    private LocalDateTime regDate;
    private String content;
    @Setter
    private Long level;
    private Long replyCount;
}
