package com.hunnit_beasts.kelog.common.event;

import com.hunnit_beasts.kelog.post.dto.response.PostLikeResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record PostLikeEvent(PostLikeResponseDTO dto) {}
