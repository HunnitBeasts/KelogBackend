package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.request.user.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.FollowDeleteResponseDTO;
import com.hunnit_beasts.kelog.dto.response.user.FollowIngResponseDTO;
import com.hunnit_beasts.kelog.entity.compositekey.FollowerId;
import com.hunnit_beasts.kelog.entity.domain.Follower;
import com.hunnit_beasts.kelog.entity.domain.User;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.handler.exception.ExpectException;
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
