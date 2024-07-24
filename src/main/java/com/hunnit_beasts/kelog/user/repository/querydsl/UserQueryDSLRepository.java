package com.hunnit_beasts.kelog.user.repository.querydsl;

import com.hunnit_beasts.kelog.auth.dto.response.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.user.dto.convert.FollowerInfos;
import com.hunnit_beasts.kelog.user.dto.response.SocialUpdateResponseDTO;

import java.util.List;

public interface UserQueryDSLRepository {

    UserCreateResponseDTO findUserCreateResponseDTOById(Long id);
    SocialUpdateResponseDTO findUserSocialsById(Long id);
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    List<FollowerInfos> findFollowerInfosByUserId(Long userId);
    List<FollowerInfos> findFolloweeInfosByUserId(Long userId);
    Long followerCountByUserId(Long userId);
    Long followeeCountByUserId(Long userId);
}
