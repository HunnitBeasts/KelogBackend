package com.hunnit_beasts.kelog.common.serviceimpl;

import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.common.repository.AlarmJpaRepository;
import com.hunnit_beasts.kelog.common.service.AlarmService;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.entity.domain.Follower;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.FollowerJpaRepository;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private final AlarmJpaRepository alarmJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final FollowerJpaRepository followerJpaRepository;

    @Override
    public void newLikeAlarm(Long senderId, PostLikeRequestDTO dto) {
        User sender = userJpaRepository.findById(senderId).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        Post post = postJpaRepository.findById(dto.getPostId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        User receiver = post.getUser();

        alarmJpaRepository.save(new Alarm(sender, receiver, AlarmType.LIKE));
    }

    @Override
    public void newFollowAlarm(Long senderId, FollowIngRequestDTO dto) {
        User sender = userJpaRepository.findById(senderId).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        User receiver = userJpaRepository.findById(dto.getFollowee()).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));

        alarmJpaRepository.save(new Alarm(sender, receiver, AlarmType.FOLLOW));
    }

    @Override
    public void newCommentAlarm(Long senderId, CommentCreateRequestDTO dto) {
        User sender = userJpaRepository.findById(senderId).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        Post post = postJpaRepository.findById(dto.getPostId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        User receiver = post.getUser();
        if(!Objects.equals(sender, receiver)) {
            alarmJpaRepository.save(new Alarm(sender, receiver, AlarmType.COMMENT));
        }

    }

    @Override
    public void newPostAlarm(Long senderId) {
        User sender = userJpaRepository.findById(senderId).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        List<Follower> followers = followerJpaRepository.findByFollowee_Id(senderId);
        List<Alarm> alarms = new ArrayList<>();

        if (!followers.isEmpty()) {
            for(Follower follower : followers) {
                User receiver = follower.getFollower();
                alarms.add(new Alarm(sender, receiver, AlarmType.SUBSCRIBE));
            }
            alarmJpaRepository.saveAll(alarms);
        }
    }

//    public AlarmResponseDTO readAlarm(AlarmId alarmId) { //안되면 queryDsl 로 짤것
//        Alarm alarm = alarmJpaRepository.findByUserId();
//
//        return new AlarmResponseDTO(alarm);
//    }
}
