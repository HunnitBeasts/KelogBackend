package com.hunnit_beasts.kelog.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentReadResponseDTO {
    private Long id;
    private String thumbImage;
    private String nickname;
    private LocalDateTime regDate;
    private String content;
    private Long replyCount;
}
