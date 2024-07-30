package com.hunnit_beasts.kelog.common.dto.response;


import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AlarmReadResponseDTO {
    private Long alarmId;
    private AlarmType alarmType;
    private String senderUserId;
    private String senderUserImage;
    private String senderNickName;
    private boolean isCheck;
    private String link;
    private LocalDateTime regDate;
    private Object detail;

    public AlarmReadResponseDTO(Alarm alarm, User sender, String link, Object detail) {
        this.alarmId = alarm.getId();
        this.alarmType = alarm.getAlarmType();
        this.senderUserId = sender.getUserId();
        this.senderUserImage = sender.getThumbImage();
        this.senderNickName = sender.getNickname();
        this.isCheck = alarm.getIsCheck();
        this.link = link;
        this.regDate = alarm.getRegDate();
        this.detail = detail;
    }
}
