package com.hunnit_beasts.kelog.common.dto.converter.impl;

import com.hunnit_beasts.kelog.common.dto.convert.AlarmPostInfo;
import com.hunnit_beasts.kelog.common.dto.converter.factory.AlarmConverter;
import com.hunnit_beasts.kelog.common.dto.converter.util.LinkUtil;
import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostAlarmConverter implements AlarmConverter {
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public AlarmReadResponseDTO convert(Alarm alarm) {

        Post post = postJpaRepository.findById(alarm.getTargetId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        User sender = userJpaRepository.findById(post.getUser().getId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));

        String link = LinkUtil.createLink(sender.getUserId(), post.getUrl());
        Object detail = new AlarmPostInfo(post.getTitle());

        return new AlarmReadResponseDTO(alarm,sender,link,detail);
    }
}
