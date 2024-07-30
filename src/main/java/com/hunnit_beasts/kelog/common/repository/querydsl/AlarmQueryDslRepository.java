package com.hunnit_beasts.kelog.common.repository.querydsl;

import com.hunnit_beasts.kelog.common.dto.convert.AlarmCommentInfos;
import com.hunnit_beasts.kelog.common.dto.convert.AlarmFollowInfos;
import com.hunnit_beasts.kelog.common.dto.convert.AlarmLikeInfos;
import com.hunnit_beasts.kelog.common.dto.convert.AlarmPostInfos;
import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;

public interface AlarmQueryDslRepository {
    AlarmReadResponseDTO findLikeAlarmReadResponseDTOById (Long alarmId);
    AlarmReadResponseDTO findPostAlarmReadResponseDTOById (Long alarmId);
    AlarmReadResponseDTO findCommentAlarmReadResponseDTOById (Long alarmId);
    AlarmReadResponseDTO findFollowAlarmReadResponseDTOById (Long alarmId);

    AlarmCommentInfos findAlarmCommentInfosById (Long alarmId);
    AlarmFollowInfos findAlarmFollowInfosById (Long alarmId);
    AlarmLikeInfos findAlarmLikeInfosById (Long alarmId);
    AlarmPostInfos findAlarmPostInfosById (Long alarmId);
}
