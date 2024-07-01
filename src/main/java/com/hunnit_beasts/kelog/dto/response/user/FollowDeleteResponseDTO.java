package com.hunnit_beasts.kelog.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowDeleteResponseDTO {
    private Long followerId;
    private Long followeeId;
}
