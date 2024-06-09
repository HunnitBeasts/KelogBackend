package com.hunnit_beasts.kelog.enumeration.system;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NO_POST_TYPE_ERROR("CODE 1",500,"[ERROR] PostType 이 NULL 입니다!"),
    NO_ALARM_TYPE_ERROR("CODE 2",500,"[ERROR] AlarmType 이 NULL 입니다!"),
    NO_SOCIAL_TYPE_ERROR("CODE 3",500,"[ERROR] SocialType 이 NULL 입니다!"),
    NO_USER_TYPE_ERROR("CODE 4",500,"[ERROR] UserType 이 NULL 입니다!"),
    ;


    private final String code;
    private final int status;
    private final String message;

    ErrorCode(String code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
