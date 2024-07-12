package com.hunnit_beasts.kelog.user.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserIntro is a Querydsl query type for UserIntro
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserIntro extends EntityPathBase<UserIntro> {

    private static final long serialVersionUID = 288151523L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserIntro userIntro = new QUserIntro("userIntro");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final QUser user;

    public QUserIntro(String variable) {
        this(UserIntro.class, forVariable(variable), INITS);
    }

    public QUserIntro(Path<? extends UserIntro> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserIntro(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserIntro(PathMetadata metadata, PathInits inits) {
        this(UserIntro.class, metadata, inits);
    }

    public QUserIntro(Class<? extends UserIntro> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

