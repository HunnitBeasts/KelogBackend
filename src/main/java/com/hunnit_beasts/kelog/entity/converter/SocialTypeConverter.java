package com.hunnit_beasts.kelog.entity.converter;

import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.enumeration.types.SocialType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) //@Convert 어노테이션 없이도 해당 이넘속성 칼럼이 기능함
public class SocialTypeConverter implements AttributeConverter<SocialType, Integer> {

    @Override // entity-> db 값 추가할때 integer로 변환
    public Integer convertToDatabaseColumn(SocialType socialType) {
        if(socialType == null)
            throw new IllegalArgumentException(ErrorCode.NO_SOCIAL_TYPE_ERROR.getCode());
        return socialType.getTypeNum();
    }

    @Override // db->entity로 가져올때 enum으로 변환
    public SocialType convertToEntityAttribute(Integer dbType) {
        return SocialType.ofNum(dbType);
    }
}
