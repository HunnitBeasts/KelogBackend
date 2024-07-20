package com.hunnit_beasts.kelog.user.repository.querydsl;

import com.hunnit_beasts.kelog.auth.dto.response.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.SocialUpdateResponseDTO;

public interface UserQueryDSLRepository {

    UserCreateResponseDTO findUserCreateResponseDTOById(Long id);
    SocialUpdateResponseDTO findUserSocialsById(Long id);
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
