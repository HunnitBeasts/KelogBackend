package com.hunnit_beasts.kelog.user.entity.compositekey;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSocialInfoId is a Querydsl query type for SocialInfoId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSocialInfoId extends BeanPath<SocialInfoId> {

    private static final long serialVersionUID = 132604480L;

    public static final QSocialInfoId socialInfoId = new QSocialInfoId("socialInfoId");

    public final EnumPath<com.hunnit_beasts.kelog.user.enumeration.SocialType> socialType = createEnum("socialType", com.hunnit_beasts.kelog.user.enumeration.SocialType.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QSocialInfoId(String variable) {
        super(SocialInfoId.class, forVariable(variable));
    }

    public QSocialInfoId(Path<? extends SocialInfoId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSocialInfoId(PathMetadata metadata) {
        super(SocialInfoId.class, metadata);
    }

}

