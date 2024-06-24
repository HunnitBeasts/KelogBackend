package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.request.user.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.FollowIngResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.Follower;
import com.hunnit_beasts.kelog.entity.domain.User;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.repository.jpa.FollowerJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final FollowerJpaRepository followerJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public FollowIngResponseDTO following(Long userId, FollowIngRequestDTO dto) {
        User following = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getMessage()));
        User followed = userJpaRepository.findById(dto.getFollowing())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getMessage()));
        Follower savedFollower = new Follower(following,followed);
        Follower follow = followerJpaRepository.save(savedFollower);
        return new FollowIngResponseDTO(follow);
    }
}
