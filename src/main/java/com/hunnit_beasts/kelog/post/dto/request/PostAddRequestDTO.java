package com.hunnit_beasts.kelog.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostAddRequestDTO {
    private Long postId;
    private Long seriesId;
}
