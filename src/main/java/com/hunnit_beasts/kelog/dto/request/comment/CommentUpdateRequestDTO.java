package com.hunnit_beasts.kelog.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateRequestDTO {
    private String content;
}
