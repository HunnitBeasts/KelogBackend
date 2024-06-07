package com.hunnit_beasts.kelog.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecentPost is a Querydsl query type for RecentPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecentPost extends EntityPathBase<RecentPost> {

    private static final long serialVersionUID = 1442439070L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecentPost recentPost = new QRecentPost("recentPost");

    public final com.hunnit_beasts.kelog.entity.superclass.QRegEntity _super = new com.hunnit_beasts.kelog.entity.superclass.QRegEntity(this);

    public final com.hunnit_beasts.kelog.entity.compositekey.QRecentPostId id;

    public final QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final QUser user;

    public QRecentPost(String variable) {
        this(RecentPost.class, forVariable(variable), INITS);
    }

    public QRecentPost(Path<? extends RecentPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecentPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecentPost(PathMetadata metadata, PathInits inits) {
        this(RecentPost.class, metadata, inits);
    }

    public QRecentPost(Class<? extends RecentPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new com.hunnit_beasts.kelog.entity.compositekey.QRecentPostId(forProperty("id")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

