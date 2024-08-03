package com.hunnit_beasts.kelog.common.event;

import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostEvent {
    private final PostCreateResponseDTO dto;
}
