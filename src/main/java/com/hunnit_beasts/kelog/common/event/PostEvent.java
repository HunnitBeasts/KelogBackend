package com.hunnit_beasts.kelog.common.event;

import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record PostEvent(PostCreateResponseDTO dto) {}
