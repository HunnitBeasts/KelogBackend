package com.hunnit_beasts.kelog.comment.dto.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentListInfo {
    private Long id;
    private String thumbImage;
    private String nickname;
    private LocalDateTime regDate;
    private String content;
    private Long replyCount;
}
