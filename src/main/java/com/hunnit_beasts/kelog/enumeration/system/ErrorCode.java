package com.hunnit_beasts.kelog.enumeration.system;

import lombok.Getter;

@Getter
public enum ErrorCode {

    OCCUR_UNKNOWN_TYPE_ERROR("CODE 0",500,"[ERROR] 알 수 없는 에러가 발생하였습니다!"),

    NO_POST_TYPE_ERROR("CODE 1",500,"[ERROR] PostType 이 NULL 입니다!"),
    NO_ALARM_TYPE_ERROR("CODE 2",500,"[ERROR] AlarmType 이 NULL 입니다!"),
    NO_SOCIAL_TYPE_ERROR("CODE 3",500,"[ERROR] SocialType 이 NULL 입니다!"),
    NO_USER_TYPE_ERROR("CODE 4",500,"[ERROR] UserType 이 NULL 입니다!"),
    NO_TARGET_TYPE_ERROR("CODE 5",500,"[ERROR] 두번째 매개변수 명이 잘못되었습니다!"),
    NOT_SAME_USERID_ERROR("CODE 6",500,"[ERROR] 본인이 아닙니다!"),
    NO_PARAMETER_ERROR("CODE 7",500,"[ERROR] 파라미터가 없습니다.!"),
    NO_USER_DATA_ERROR("CODE 8",400,"[ERROR] 유저데이터가 없습니다!"),
    NO_POST_DATA_ERROR("CODE 9",500,"[ERROR] 게시물 데이터가 없습니다!"),
    NOT_SAME_POST_ID_ERROR("CODE 10",500,"[ERROR] 본인의 게시물이 아닙니다!"),

    NOT_SUPPORTED_ENDPOINT_ERROR("CODE 11",415,"[ERROR] 정의되지 않은 기능입니다!"),
    FILE_UPLOAD_FAILURE_ERROR("CODE 12",500,"[ERROR] 파일을 업로드하지 못했습니다!"),
    NOT_SUPPORTED_OS_ERROR("CODE 13", 500, "[ERROR] 지원하지 않는 OS 입니다!"),
    FILE_SIZE_OVER_ERROR("CODE 14",400,"[ERROR] 파일의 크기가 너무 큽니다!"),
    NOT_FILE_TYPE_ERROR("CODE 15", 400, "[ERROR] 잘못된 파일 종류입니다!")
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
