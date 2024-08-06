package com.hunnit_beasts.kelog.common.dto.converter.impl;

import com.hunnit_beasts.kelog.common.dto.convert.AlarmLikeInfo;
import com.hunnit_beasts.kelog.common.dto.converter.factory.AlarmConverter;
import com.hunnit_beasts.kelog.common.dto.converter.util.LinkUtil;
import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.post.entity.domain.LikedPost;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.repository.jpa.LikedPostJpaRepository;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeAlarmConverter implements AlarmConverter {

    private final LikedPostJpaRepository likedPostJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public AlarmReadResponseDTO convert(Alarm alarm) {

        LikedPost likedPost = likedPostJpaRepository.findById(alarm.getTargetId()).orElseThrow(()->new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        Post post = likedPost.getPost();
        User sender = userJpaRepository.findById(likedPost.getUser().getId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));

        Object detail = new AlarmLikeInfo(post.getTitle());
        String link = LinkUtil.createLink(sender.getUserId(), post.getUrl());

        return new AlarmReadResponseDTO(alarm,sender,link,detail);
    }
}
