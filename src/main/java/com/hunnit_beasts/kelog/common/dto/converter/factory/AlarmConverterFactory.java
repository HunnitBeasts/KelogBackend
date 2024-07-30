package com.hunnit_beasts.kelog.common.dto.converter.factory;

import com.hunnit_beasts.kelog.common.dto.converter.impl.CommentAlarmConverter;
import com.hunnit_beasts.kelog.common.dto.converter.impl.FollowAlarmConverter;
import com.hunnit_beasts.kelog.common.dto.converter.impl.LikeAlarmConverter;
import com.hunnit_beasts.kelog.common.dto.converter.impl.PostAlarmConverter;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmConverterFactory {

    private final CommentAlarmConverter commentAlarmConverter;
    private final FollowAlarmConverter followAlarmConverter;
    private final LikeAlarmConverter likeAlarmConverter;
    private final PostAlarmConverter postAlarmConverter;

    public AlarmConverter getAlarmConverter(AlarmType alarmType) {
        return switch (alarmType){
            case LIKE -> likeAlarmConverter;
            case COMMENT -> commentAlarmConverter;
            case FOLLOW -> followAlarmConverter;
            case SUBSCRIBE -> postAlarmConverter;
        };
    }
}
