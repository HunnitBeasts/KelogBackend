package com.hunnit_beasts.kelog.enumeration.system;

import com.hunnit_beasts.kelog.manager.ErrorMessageManager;
import lombok.Getter;

@Getter
public enum ErrorCode {

    /*U error type: 원인을 알 수 없는 에러, default status: 500*/
    OCCUR_UNKNOWN_TYPE_ERROR("CODE U-000"),

    /*A error type: TYPE 이 NULL 인 경우 에러, default status: 500*/
    NO_POST_TYPE_ERROR("CODE A-001"),
    NO_ALARM_TYPE_ERROR("CODE A-002"),
    NO_SOCIAL_TYPE_ERROR("CODE A-003"),
    NO_USER_TYPE_ERROR("CODE A-004"),

    /*B error type: 조회한 데이터가 없을 경우 에러, default status: 404*/
    NO_USER_DATA_ERROR("CODE B-001"),
    NO_POST_DATA_ERROR("CODE B-002"),
    NO_POST_VIEW_DATA_ERROR("CODE B-003"),
    NO_IMAGE_DATA_ERROR("CODE B-004"),
    NO_COMMENT_DATA_ERROR("CODE B-005"),
    NO_FOLLOW_DATA_ERROR("CODE B-006"),
    NO_SERIES_DATA_ERROR("CODE B-007"),

    /*C error type: 잘못된 입력 에러, default status: 409*/
    FILE_SIZE_OVER_ERROR("CODE C-001",413),
    NOT_FILE_TYPE_ERROR("CODE C-002", 415),
    POST_LIKE_DUPLICATION_ERROR("CODE C-003"),
    DUPLICATION_FOLLOW_ERROR("CODE C-004"),
    NOT_SUPPORTED_OS_ERROR("CODE C-005", 400),

    /*D error type: 잘못된 권한 에러, default status: 403*/
    NOT_SAME_USERID_ERROR("CODE D-001"),
    NOT_SAME_POST_ID_ERROR("CODE D-002"),
    NOT_SAME_COMMENT_ID_ERROR("CODE D-003"),
    NOT_SAME_SERIES_ID_ERROR("CODE D-004"),

    /*E error type: 서비스 실패 에러, default status: 500*/
    FILE_UPLOAD_FAILURE_ERROR("CODE E-001"),

    /*F error type: 서버 에러, default status: 400*/
    NO_PARAMETER_ERROR("CODE F-001"),
    NOT_SUPPORTED_ENDPOINT_ERROR("CODE F-002",415),
    NO_TARGET_TYPE_ERROR("CODE F-003"),
    ;


    private final String code;
    private final int status;
    private final String message;

    ErrorCode(String code){
        this.code = code;
        this.status = checkStatus(code);
        this.message = ErrorMessageManager.getMessages(this.name());
    }

    ErrorCode(String code, int status){
        this.code = code;
        this.status = status;
        this.message = ErrorMessageManager.getMessages(this.name());
    }

    private int checkStatus(String code){
        if(code.startsWith("CODE U")) return 500;

        if(code.startsWith("CODE A")) return 500;

        if(code.startsWith("CODE B")) return 404;

        if(code.startsWith("CODE C")) return 409;

        if(code.startsWith("CODE D")) return 403;

        if(code.startsWith("CODE E")) return 500;

        if(code.startsWith("CODE F")) return 400;

        return 500;
    }
}
