package com.hunnit_beasts.kelog.common.dto.response;


import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmResponseDTO {
    private AlarmType alarmType;
    private Long senderId;
    private Long receiverId;
    private String senderName;
//    private String alarmContent;
    private boolean isCheck;

    public AlarmResponseDTO(Alarm alarm){ //닉네임은 서비스에서 가져와서 초기화하는 방식 고려할것
        this.alarmType = alarm.getAlarmType();
        this.senderId = alarm.getUser().getId();
        this.receiverId = alarm.getTarget().getId();
        this.senderName = alarm.getUser().getNickname();
        this.isCheck = false;
    }
}
