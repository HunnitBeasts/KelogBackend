package com.hunnit_beasts.kelog.dto.response.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentUpdateResponseDTO {
    private Long commentId;
    private String content;
    private LocalDateTime modDate;
}
