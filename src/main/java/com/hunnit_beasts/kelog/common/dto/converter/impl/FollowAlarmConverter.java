package com.hunnit_beasts.kelog.common.dto.converter.impl;

import com.hunnit_beasts.kelog.common.dto.convert.AlarmFollowInfo;
import com.hunnit_beasts.kelog.common.dto.converter.factory.AlarmConverter;
import com.hunnit_beasts.kelog.common.dto.converter.util.LinkUtil;
import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.user.entity.compositekey.FollowerId;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.FollowerJpaRepository;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowAlarmConverter implements AlarmConverter {
    private final UserJpaRepository userJpaRepository;
    private final FollowerJpaRepository followerJpaRepository;

    @Override
    public AlarmReadResponseDTO convert(Alarm alarm) {

        User sender = userJpaRepository.findById(alarm.getTargetId()).orElseThrow(
                ()-> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));

        String link = LinkUtil.createLink(sender.getUserId());
        Object detail = new AlarmFollowInfo(followerJpaRepository.existsById(
                new FollowerId(alarm.getUser(),sender)));

        return new AlarmReadResponseDTO(alarm,sender,link,detail);
    }
}
