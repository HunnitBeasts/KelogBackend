package com.hunnit_beasts.kelog.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentCreateResponseDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
