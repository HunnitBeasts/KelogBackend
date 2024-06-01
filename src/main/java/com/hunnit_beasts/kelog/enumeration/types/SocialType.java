package com.hunnit_beasts.kelog.enumeration.types;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import lombok.Getter;

@Getter
public enum SocialType {

    EMAIL(0,"EMAIL","이메일"),
    GITHUB(1,"GITHUB","깃허브"),
    INSTAGRAM(2,"INSTAGRAM","인스타그램"),
    FACEBOOK(3,"FACEBOOK","페이스북"),
    HOMEPAGE(4,"HOMEPAGE","홈페이지"),
    ;

    private final Integer typeNum;
    private final String code;
    private final String korean;

    SocialType(int typeNum, String code, String korean) {
        this.typeNum = typeNum;
        this.code = code;
        this.korean = korean;
    }

    public static SocialType ofNum(Integer value) {
        for (SocialType socialType : SocialType.values())
            if(socialType.getTypeNum().equals(value))
                return socialType;
        throw new IllegalArgumentException(ErrorCode.NO_SOCIAL_TYPE_ERROR.getMessage());
    }
}
