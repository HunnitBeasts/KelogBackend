package com.hunnit_beasts.kelog.dto.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentUpdateResponseDTO {
    private Long commentId;
    private String content;
    private LocalDateTime modDate;
}
