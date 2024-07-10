package com.hunnit_beasts.kelog.user.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSocialInfo is a Querydsl query type for SocialInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSocialInfo extends EntityPathBase<SocialInfo> {

    private static final long serialVersionUID = 1471388537L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSocialInfo socialInfo = new QSocialInfo("socialInfo");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    public final EnumPath<com.hunnit_beasts.kelog.user.enumeration.SocialType> type = createEnum("type", com.hunnit_beasts.kelog.user.enumeration.SocialType.class);

    public final QUser user;

    public QSocialInfo(String variable) {
        this(SocialInfo.class, forVariable(variable), INITS);
    }

    public QSocialInfo(Path<? extends SocialInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSocialInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSocialInfo(PathMetadata metadata, PathInits inits) {
        this(SocialInfo.class, metadata, inits);
    }

    public QSocialInfo(Class<? extends SocialInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

