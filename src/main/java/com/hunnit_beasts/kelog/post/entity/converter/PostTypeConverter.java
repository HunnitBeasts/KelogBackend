package com.hunnit_beasts.kelog.post.entity.converter;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PostTypeConverter implements AttributeConverter<PostType,Integer> {

    @Override //entity-> db 값 추가할때 integer로 변환
    public Integer convertToDatabaseColumn(PostType postType) {
        if(postType == null)
            throw new ExpectException(ErrorCode.NO_POST_TYPE_ERROR); // TODO : 나중에 service 분리 하자잇
        return postType.getTypeNum();
    }

    @Override //db->entity로 가져올때 enum으로 변환
    public PostType convertToEntityAttribute(Integer dbType) {
        return PostType.ofNum(dbType);
    }

}