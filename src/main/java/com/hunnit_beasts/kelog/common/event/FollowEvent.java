package com.hunnit_beasts.kelog.common.event;

import com.hunnit_beasts.kelog.user.dto.response.FollowIngResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowEvent {
    private final FollowIngResponseDTO dto;
}
