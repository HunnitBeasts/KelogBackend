package com.hunnit_beasts.kelog.enumeration.types;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import lombok.Getter;

@Getter
public enum PostType {
    NORMAL(0), //일반게시물
    INCOMPLETE(1), //임시 게시물
    NOTICE(2); //공지사항

    //enum이 가지고있는 값 반환
    private final Integer typeNum;

    PostType(Integer typeNum) {this.typeNum = typeNum;}

    public static PostType ofNum(Integer typeNum) {
        for (PostType postType : PostType.values())
            if (postType.getTypeNum().equals(typeNum))
                return postType;
        throw new IllegalArgumentException(ErrorCode.NO_POST_TYPE_ERROR.getMessage()); // TODO : 서비스분리
    }
}
