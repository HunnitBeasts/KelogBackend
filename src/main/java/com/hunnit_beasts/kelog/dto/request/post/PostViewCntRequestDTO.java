package com.hunnit_beasts.kelog.dto.request.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostViewCntRequestDTO {
    private Long postId;
}
