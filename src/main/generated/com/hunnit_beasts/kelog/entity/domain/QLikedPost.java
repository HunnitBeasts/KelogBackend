package com.hunnit_beasts.kelog.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikedPost is a Querydsl query type for LikedPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikedPost extends EntityPathBase<LikedPost> {

    private static final long serialVersionUID = 429372298L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikedPost likedPost = new QLikedPost("likedPost");

    public final com.hunnit_beasts.kelog.entity.superclass.QRegEntity _super = new com.hunnit_beasts.kelog.entity.superclass.QRegEntity(this);

    public final com.hunnit_beasts.kelog.entity.compositekey.QLikedPostId likedPostId;

    public final QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final QUser user;

    public QLikedPost(String variable) {
        this(LikedPost.class, forVariable(variable), INITS);
    }

    public QLikedPost(Path<? extends LikedPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikedPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikedPost(PathMetadata metadata, PathInits inits) {
        this(LikedPost.class, metadata, inits);
    }

    public QLikedPost(Class<? extends LikedPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.likedPostId = inits.isInitialized("likedPostId") ? new com.hunnit_beasts.kelog.entity.compositekey.QLikedPostId(forProperty("likedPostId")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

