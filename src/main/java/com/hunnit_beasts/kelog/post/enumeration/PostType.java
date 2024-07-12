package com.hunnit_beasts.kelog.post.enumeration;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import lombok.Getter;

@Getter
public enum PostType {
    NORMAL(0, "NORMAL", "일반 게시물"),
    INCOMPLETE(1, "INCOMPLETE", "임시 게시물"),
    NOTICE(2, "NOTICE", "공지사항"),
    ;

    //enum이 가지고있는 값 반환
    private final Integer typeNum;
    private final String code;
    private final String type;

    PostType(Integer typeNum,String code, String type) {
        this.typeNum = typeNum;
        this.code = code;
        this.type = type;
    }

    public static PostType ofNum(Integer typeNum) {
        for (PostType postType : PostType.values())
            if (postType.getTypeNum().equals(typeNum))
                return postType;
        throw new IllegalArgumentException(ErrorCode.NO_POST_TYPE_ERROR.getCode()); // TODO : 서비스분리
    }
}
