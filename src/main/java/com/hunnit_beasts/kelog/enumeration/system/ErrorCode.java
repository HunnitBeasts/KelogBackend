package com.hunnit_beasts.kelog.enumeration.system;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NO_POST_TYPE_ERROR("CODE 1",500,"[ERROR] PostType 이 NULL 입니다!"),
    NO_ALARM_TYPE_ERROR("CODE 2",500,"[ERROR] AlarmType 이 NULL 입니다!"),
    NO_SOCIAL_TYPE_ERROR("CODE 3",500,"[ERROR] SocialType 이 NULL 입니다!"),
    NO_USER_TYPE_ERROR("CODE 4",500,"[ERROR] UserType 이 NULL 입니다!"),
    NO_TARGET_TYPE_ERROR("CODE 5",500,"[ERROR] 두번째 매개변수 명이 잘못되었습니다!"),
    NOT_SAME_USERID_ERROR("CODE 6",500,"[ERROR] 본인이 아닙니다!"),
    NO_PARAMETER_ERROR("CODE 7",500,"[ERROR] 파라미터가 없습니다.!"),
    NO_USER_DATA_ERROR("CODE 8",500,"[ERROR] 유저데이터가 없습니다!"),
    NO_POST_DATA_ERROR("CODE 9",500,"[ERROR] 게시물 데이터가 없습니다!"),
    NOT_SAME_POST_ID_ERROR("CODE 10",500,"[ERROR] 본인의 게시물이 아닙니다!"),
    POST_LIKE_DUPLICATION_ERROR("CODE 11",500,"[ERROR] 이미 좋아요를 누른 게시물입니다!"),
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
