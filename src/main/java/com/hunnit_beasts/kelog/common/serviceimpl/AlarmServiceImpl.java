package com.hunnit_beasts.kelog.common.serviceimpl;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.comment.repository.CommentJpaRepository;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.common.repository.AlarmJpaRepository;
import com.hunnit_beasts.kelog.common.service.AlarmService;
import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostLikeResponseDTO;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.user.dto.response.FollowIngResponseDTO;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.FollowerJpaRepository;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private final AlarmJpaRepository alarmJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final FollowerJpaRepository followerJpaRepository;

    @Override
    public void newLikeAlarm(Long senderId, PostLikeResponseDTO dto, AlarmType alarmType) {
        User sender = userJpaRepository.findById(senderId).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        Post post = postJpaRepository.findById(dto.getPostId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        User receiver = post.getUser();

        alarmJpaRepository.save(new Alarm(sender, receiver, alarmType));
    }

    @Override
    public void newFollowAlarm(Long senderId, FollowIngResponseDTO dto, AlarmType alarmType) {
        User sender = userJpaRepository.findById(senderId).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        User receiver = userJpaRepository.findById(dto.getFollowee()).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));

        alarmJpaRepository.save(new Alarm(sender, receiver, alarmType));
    }

    @Override
    public void newCommentAlarm(Long senderId, CommentCreateResponseDTO dto, AlarmType alarmType) {
        User sender = userJpaRepository.findById(senderId).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        Post post = postJpaRepository.findById(dto.getPostId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        User receiver = post.getUser();

        alarmJpaRepository.save(new Alarm(sender, receiver, alarmType));
    }

    @Override
    public void newPostAlarm(Long senderId, FollowIngResponseDTO dto, AlarmType alarmType) {
        User sender = userJpaRepository.findById(senderId).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        User receiver = userJpaRepository.findById(dto.getFollowee()).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));


    }
}
