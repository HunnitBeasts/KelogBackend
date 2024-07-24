package com.hunnit_beasts.kelog.user.serviceimpl;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.user.dto.convert.FollowerInfos;
import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowDeleteResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowIngResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowerReadResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.SocialUpdateResponseDTO;
import com.hunnit_beasts.kelog.user.entity.compositekey.FollowerId;
import com.hunnit_beasts.kelog.user.entity.compositekey.SocialInfoId;
import com.hunnit_beasts.kelog.user.entity.domain.Follower;
import com.hunnit_beasts.kelog.user.entity.domain.Social;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.FollowerJpaRepository;
import com.hunnit_beasts.kelog.user.repository.jpa.SocialJpaRepository;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.user.repository.querydsl.UserQueryDSLRepository;
import com.hunnit_beasts.kelog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final FollowerJpaRepository followerJpaRepository;
    private final SocialJpaRepository socialJpaRepository;
    private final UserJpaRepository userJpaRepository;

    private final UserQueryDSLRepository userQueryDSLRepository;

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

    @Override
    public SocialUpdateResponseDTO socialUpdate(Long userId, List<SocialInfos> socials) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        for (SocialInfos social : socials) {
            SocialInfoId id = new SocialInfoId(userId, social.getSocialType());
            processSocialInfo(social, id, user);
        }

        return userQueryDSLRepository.findUserSocialsById(userId);
    }

    @Override
    public FollowerReadResponseDTO readFollower(Long userId) {
        List<FollowerInfos> infos = userQueryDSLRepository.findFollowerInfosByUserId(userId);
        Long count = userQueryDSLRepository.followerCountByUserId(userId);
        return new FollowerReadResponseDTO(count,infos);
    }

    @Override
    public FollowerReadResponseDTO readFollowee(Long userId) {
        List<FollowerInfos> infos = userQueryDSLRepository.findFolloweeInfosByUserId(userId);
        Long count = userQueryDSLRepository.followeeCountByUserId(userId);
        return new FollowerReadResponseDTO(count,infos);
    }

    private void processSocialInfo(SocialInfos social, SocialInfoId id, User user) {
        if (social.getUrl().isEmpty()) {
            if (socialJpaRepository.existsById(id))
                socialJpaRepository.deleteById(id);
        }else {
            if (socialJpaRepository.existsById(id)){
                Social socialInfo = socialJpaRepository.findById(id)
                        .orElseThrow(() -> new ExpectException(ErrorCode.NO_SOCIAL_DATA_ERROR));
                if (!socialInfo.getLink().equals(social.getUrl()))
                    socialJpaRepository.save(socialInfo.changeUrl(social.getUrl()));
            }else
                socialJpaRepository.save(new Social(social, user));
        }
    }
}
