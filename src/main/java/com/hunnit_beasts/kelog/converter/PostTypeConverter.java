package com.hunnit_beasts.kelog.converter;

import com.hunnit_beasts.kelog.enumeration.PostType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PostTypeConverter implements AttributeConverter<PostType,Integer> {

    @Override //entity-> db 값 추가할때 integer로 변환
    public Integer convertToDatabaseColumn(PostType postType) {
        if(postType == null){
            throw new IllegalArgumentException("PostType 이 NULL 입니다!");
        }
        return postType.getTypeNum();
    }

    @Override //db->entity로 가져올때 enum으로 변환
    public PostType convertToEntityAttribute(Integer dbType) {
        return PostType.ofNum(dbType);
    }

}
