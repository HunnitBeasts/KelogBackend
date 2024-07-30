package com.hunnit_beasts.kelog.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentReadDTO {
    Long userId;
    String thumbImage;
    String nickname;
    LocalDateTime regDate;
    String content;
}
