package com.hunnit_beasts.kelog.service;

import com.hunnit_beasts.kelog.dto.request.user.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.FollowDeleteResponseDTO;
import com.hunnit_beasts.kelog.dto.response.user.FollowIngResponseDTO;

public interface UserService {
    FollowIngResponseDTO following(Long userId, FollowIngRequestDTO dto);
    FollowDeleteResponseDTO unFollow(Long follower, Long followee);
}
