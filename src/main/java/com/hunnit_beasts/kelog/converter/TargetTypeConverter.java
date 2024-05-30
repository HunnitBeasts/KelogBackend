package com.hunnit_beasts.kelog.converter;

import com.hunnit_beasts.kelog.enumeration.PostType;
import com.hunnit_beasts.kelog.enumeration.TargetType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) //@Convert 어노테이션 없이도 해당 이넘속성 칼럼이 기능함
public class TargetTypeConverter implements AttributeConverter<TargetType, Integer> {

    @Override //entity-> db 값 추가할때 integer로 변환
    public Integer convertToDatabaseColumn(TargetType targetType) {
        if(targetType == null){
            throw new IllegalArgumentException("PostType 이 NULL 입니다!");
        }
        return targetType.getValue();
    }

    @Override //db->entity로 가져올때 enum으로 변환
    public TargetType convertToEntityAttribute(Integer dbType) {
        return TargetType.ofNum(dbType);
    }
}
