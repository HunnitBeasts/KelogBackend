package com.hunnit_beasts.kelog.common.enumeration;

import lombok.Getter;

@Getter
public enum AlarmType {
    LIKE(0,"좋아요 받았을때 (user-post)"),
    FOLLOW(1,"팔로잉 받았을때 (user-user)"),
    COMMENT(2,"댓글이 달렸을때 (user-comment)"),
    SUBSCRIBE(3,"구독한 사람이 글 올렸을때 (post-user)"),
    ;

    private final Integer typeNum;
    private final String type;

    AlarmType(Integer typeNum, String type) {
        this.typeNum = typeNum;
        this.type = type;
    }

    public static AlarmType ofNum(Integer value) {
        for (AlarmType alarmType : AlarmType.values())
            if(alarmType.getTypeNum().equals(value))
                return alarmType;
        throw new IllegalArgumentException(ErrorCode.NO_ALARM_TYPE_ERROR.getCode());
    }
}
