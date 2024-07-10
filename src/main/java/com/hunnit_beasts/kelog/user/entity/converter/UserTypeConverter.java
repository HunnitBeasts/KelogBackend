package com.hunnit_beasts.kelog.user.entity.converter;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) //@Convert 어노테이션 없이도 해당 이넘속성 칼럼이 기능함
public class UserTypeConverter implements AttributeConverter<UserType, Integer> {

    @Override // entity-> db 값 추가할때 integer로 변환
    public Integer convertToDatabaseColumn(UserType alarmType) {
        if(alarmType == null)
            throw new ExpectException(ErrorCode.NO_ALARM_TYPE_ERROR);
        return alarmType.getOrder();
    }

    @Override // db->entity로 가져올때 enum으로 변환
    public UserType convertToEntityAttribute(Integer dbType) {
        return UserType.ofNum(dbType);
    }
}
