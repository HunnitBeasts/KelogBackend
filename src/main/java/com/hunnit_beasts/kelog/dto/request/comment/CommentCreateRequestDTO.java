package com.hunnit_beasts.kelog.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequestDTO {
    private Long postId;
    private String content;
}
