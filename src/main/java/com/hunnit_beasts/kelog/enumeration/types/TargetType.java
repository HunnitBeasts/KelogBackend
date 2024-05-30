package com.hunnit_beasts.kelog.enumeration.types;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import lombok.Getter;

@Getter
public enum TargetType {
    LIKE(0), //좋아요 받았을때 (user-post)
    FOLLOW(1), //팔로잉 받았을때 (user-user)
    COMMENT(2), //댓글이 달렸을떄 (user-comment)
    SUBSCRIBE(3); //구독한 사람이 글 올렸을때 (post-user)

    private final Integer value;

    TargetType(Integer value) {this.value = value;}

    public static TargetType ofNum(Integer value) {
        for (TargetType targetType : TargetType.values())
            if(targetType.getValue().equals(value))
                return targetType;
        throw new IllegalArgumentException(ErrorCode.NO_TARGET_TYPE_ERROR.getMessage());
    }
}
