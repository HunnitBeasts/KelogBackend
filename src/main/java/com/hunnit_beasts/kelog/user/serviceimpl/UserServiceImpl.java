package com.hunnit_beasts.kelog.user.serviceimpl;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowDeleteResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowIngResponseDTO;
import com.hunnit_beasts.kelog.user.entity.compositekey.FollowerId;
import com.hunnit_beasts.kelog.user.entity.domain.Follower;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.FollowerJpaRepository;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final FollowerJpaRepository followerJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public FollowIngResponseDTO following(Long userId, FollowIngRequestDTO dto) {
        User follower = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        User followee = userJpaRepository.findById(dto.getFollowee())
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        if(followerJpaRepository.existsById(new FollowerId(follower,followee)))
            throw new ExpectException(ErrorCode.DUPLICATION_FOLLOW_ERROR);
        Follower follow = followerJpaRepository.save(new Follower(follower,followee));
        return new FollowIngResponseDTO(follow);
    }

    @Override
    public FollowDeleteResponseDTO unFollow(Long follower, Long followee) {
        FollowerId followerId = new FollowerId(follower,followee);
        if(followerJpaRepository.existsById(followerId))
            followerJpaRepository.deleteById(followerId);
        else
            throw new ExpectException(ErrorCode.NO_FOLLOW_DATA_ERROR);
        return new FollowDeleteResponseDTO(follower, followee);
    }
}
