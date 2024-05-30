package com.hunnit_beasts.kelog.enumeration.system;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NO_POST_TYPE_ERROR("CODE 1",500,"[ERROR] PostType 이 NULL 입니다!"),
    NO_TARGET_TYPE_ERROR("CODE 2",500,"[ERROR] TargetType 이 NULL 입니다!");


    private final String code;
    private final int status;
    private final String message;

    ErrorCode(String code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
