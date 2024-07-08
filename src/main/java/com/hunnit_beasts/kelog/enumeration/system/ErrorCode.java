package com.hunnit_beasts.kelog.enumeration.system;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /*U error type: 원인을 알 수 없는 에러*/
    OCCUR_UNKNOWN_TYPE_ERROR("CODE U-000",500,"[ERROR] 알 수 없는 에러가 발생하였습니다!"),

    /*A error type: TYPE 이 NULL 인 경우 에러*/
    NO_POST_TYPE_ERROR("CODE A-001",500,"[ERROR] PostType 이 NULL 입니다!"),
    NO_ALARM_TYPE_ERROR("CODE A-002",500,"[ERROR] AlarmType 이 NULL 입니다!"),
    NO_SOCIAL_TYPE_ERROR("CODE A-003",500,"[ERROR] SocialType 이 NULL 입니다!"),
    NO_USER_TYPE_ERROR("CODE A-004",500,"[ERROR] UserType 이 NULL 입니다!"),

    /*B error type: 조회한 데이터가 없을 경우 에러*/
    NO_USER_DATA_ERROR("CODE B-001",404,"[ERROR] 유저데이터가 없습니다!"),
    NO_POST_DATA_ERROR("CODE B-002",404,"[ERROR] 게시물 데이터가 없습니다!"),
    NO_POST_VIEW_DATA_ERROR("CODE B-003",404,"[ERROR] 조회수 정보가 없습니다."),
    NO_IMAGE_DATA_ERROR("CODE B-004",404,"[ERROR] 요청하신 이미지가 없습니다!"),
    NO_COMMENT_DATA_ERROR("CODE B-005",404,"[ERROR] 댓글 데이터가 없습니다!"),
    NO_FOLLOW_DATA_ERROR("CODE B-006",404,"[ERROR] 팔로우 정보가 없습니다!"),
    NO_SERIES_DATA_ERROR("CODE B-007",404,"[ERROR] 시리즈 정보가 없습니다!"),

    /*C error type: 잘못된 입력 에러*/
    FILE_SIZE_OVER_ERROR("CODE C-001",413,"[ERROR] 파일의 크기가 너무 큽니다!"),
    NOT_FILE_TYPE_ERROR("CODE C-002", 415, "[ERROR] 잘못된 파일 종류입니다!"),
    POST_LIKE_DUPLICATION_ERROR("CODE C-003",409,"[ERROR] 이미 좋아요를 누른 게시물입니다!"),
    DUPLICATION_FOLLOW_ERROR("CODE C-004",409,"[ERROR] 이미 팔로우한 유저입니다!"),
    NOT_SUPPORTED_OS_ERROR("CODE C-005", 400, "[ERROR] 지원하지 않는 OS 입니다!"),
    DUPLICATION_SERIES_POST_ERROR("CODE C-006",409,"[ERROR] 이미 시리즈에 존재하는 포스트입니다!"),

    /*D error type: 잘못된 권한 에러*/
    NOT_SAME_USERID_ERROR("CODE D-001",403,"[ERROR] 본인이 아닙니다!"),
    NOT_SAME_POST_ID_ERROR("CODE D-002",403,"[ERROR] 본인의 게시물이 아닙니다!"),
    NOT_SAME_COMMENT_ID_ERROR("CODE D-003",403,"[ERROR] 본인의 댓글이 아닙니다!"),
    NOT_SAME_SERIES_ID_ERROR("CODE D-004",403,"[ERROR] 본인의 시리즈가 아닙니다!"),

    /*E error type: 서비스 실패 에러*/
    FILE_UPLOAD_FAILURE_ERROR("CODE E-001",500,"[ERROR] 파일을 업로드하지 못했습니다!"),

    /*F error type: 서버 에러*/
    NO_PARAMETER_ERROR("CODE F-001",400,"[ERROR] 파라미터가 없습니다!"),
    NOT_SUPPORTED_ENDPOINT_ERROR("CODE F-002",415,"[ERROR] 정의되지 않은 기능입니다!"),
    NO_TARGET_TYPE_ERROR("CODE F-003",400,"[ERROR] 두번째 매개변수 명이 잘못되었습니다!"),
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
