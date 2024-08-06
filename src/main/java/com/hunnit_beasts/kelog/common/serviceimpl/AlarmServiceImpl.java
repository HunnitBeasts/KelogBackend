package com.hunnit_beasts.kelog.common.serviceimpl;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.common.dto.converter.AlarmDtoConverter;
import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.common.repository.jpa.AlarmJpaRepository;
import com.hunnit_beasts.kelog.common.service.AlarmService;
import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostLikeResponseDTO;
import com.hunnit_beasts.kelog.post.entity.domain.LikedPost;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.repository.jpa.LikedPostJpaRepository;
import com.hunnit_beasts.kelog.post.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.user.dto.response.FollowIngResponseDTO;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.user.repository.querydsl.FollowerQueryDSLRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AlarmServiceImpl implements AlarmService {
    private final AlarmJpaRepository alarmJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final AlarmDtoConverter converter;
    private final LikedPostJpaRepository likedPostJpaRepository;

    private final FollowerQueryDSLRepository followerQueryDSLRepository;

    @Async
    @Override
    public void newLikeAlarm(PostLikeResponseDTO dto) {

        Post post = postJpaRepository.findById(dto.getPostId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        User receiver = userJpaRepository.findById(post.getUser().getId()).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        LikedPost likedPost = likedPostJpaRepository.findByPost_IdAndUser_Id(dto.getPostId(), dto.getUserId());

        alarmJpaRepository.save(new Alarm(receiver, likedPost.getId(), AlarmType.LIKE));

    }

    @Async
    @Override
    public void newFollowAlarm(FollowIngResponseDTO dto) {

        Long followerId = dto.getFollower();
        User receiver = userJpaRepository.findById(dto.getFollowee()).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));

        alarmJpaRepository.save(new Alarm(receiver, followerId, AlarmType.FOLLOW));
    }

    @Async
    @Override
    public void newCommentAlarm(CommentCreateResponseDTO dto) {
        User sender = userJpaRepository.findById(dto.getUserId()).orElseThrow(()->new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        Post post = postJpaRepository.findById(dto.getPostId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        User receiver = post.getUser();

        Long commentId = dto.getId();

        if(!Objects.equals(sender, receiver)) {
            alarmJpaRepository.save(new Alarm(receiver, commentId, AlarmType.COMMENT));
        }
    }

    @Async
    @Override
    public void newPostAlarm(PostCreateResponseDTO dto) {
        Long senderId = dto.getUserId();
        List<User> followers = followerQueryDSLRepository.findFollowerByFolloweeId(senderId);
        List<Alarm> alarms = new ArrayList<>();
        Long postId = dto.getId();

        if (!followers.isEmpty()) {
            for(User follower : followers) {
                alarms.add(new Alarm(follower, postId, AlarmType.SUBSCRIBE));
            }
            alarmJpaRepository.saveAll(alarms);
        }
    }

    @Override
    public List<AlarmReadResponseDTO> readAlarm(Long userId) {
        List<Alarm> alarms = alarmJpaRepository.orderByRegDateDesc(
                alarmJpaRepository.findByUser_Id(userId));
        List<AlarmReadResponseDTO> dtos = new ArrayList<>();
        for(Alarm alarm : alarms) {
            dtos.add(converter.convert(alarm));
        }
        return dtos;
    }

    @Override
    public List<Long> deleteAllAlarm(Long userId){
        List<Alarm> alarms = alarmJpaRepository.findByUser_Id(userId);
        List<Long> deletedIds = new ArrayList<>();
        for (Alarm deleteAlarm : alarms){
            deletedIds.add(deleteAlarm.getId());
        }
        alarmJpaRepository.deleteAll(alarms);

        return deletedIds;
    }
}
