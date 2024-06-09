package com.hunnit_beasts.kelog.enumeration.types;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import lombok.Getter;

@Getter
public enum UserType {

    ADMIN(0,"ADMIN","어드민"),
    USER(1,"USER","유저"),
    ;

    private final Integer order;
    private final String type;
    private final String korean;

    UserType(int order, String type, String korean) {
        this.order = order;
        this.type = type;
        this.korean = korean;
    }

    public static UserType ofNum(Integer value) {
        for (UserType userType : UserType.values())
            if(userType.getOrder().equals(value))
                return userType;
        throw new IllegalArgumentException(ErrorCode.NO_USER_TYPE_ERROR.getMessage());
    }
}
