package com.hunnit_beasts.kelog.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFollower is a Querydsl query type for Follower
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFollower extends EntityPathBase<Follower> {

    private static final long serialVersionUID = 1844836065L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFollower follower = new QFollower("follower");

    public final com.hunnit_beasts.kelog.entity.superclass.QRegEntity _super = new com.hunnit_beasts.kelog.entity.superclass.QRegEntity(this);

    public final QUser followed;

    public final QUser following;

    public final com.hunnit_beasts.kelog.entity.compositekey.QFollowerId id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QFollower(String variable) {
        this(Follower.class, forVariable(variable), INITS);
    }

    public QFollower(Path<? extends Follower> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFollower(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFollower(PathMetadata metadata, PathInits inits) {
        this(Follower.class, metadata, inits);
    }

    public QFollower(Class<? extends Follower> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.followed = inits.isInitialized("followed") ? new QUser(forProperty("followed"), inits.get("followed")) : null;
        this.following = inits.isInitialized("following") ? new QUser(forProperty("following"), inits.get("following")) : null;
        this.id = inits.isInitialized("id") ? new com.hunnit_beasts.kelog.entity.compositekey.QFollowerId(forProperty("id")) : null;
    }

}

