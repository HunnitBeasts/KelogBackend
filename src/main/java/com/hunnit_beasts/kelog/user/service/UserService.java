package com.hunnit_beasts.kelog.user.service;

import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowDeleteResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowIngResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.SocialUpdateResponseDTO;

import java.util.List;

public interface UserService {
    FollowIngResponseDTO following(Long userId, FollowIngRequestDTO dto);
    FollowDeleteResponseDTO unFollow(Long follower, Long followee);
    SocialUpdateResponseDTO socialUpdate(Long userId, List<SocialInfos> socials);
}
