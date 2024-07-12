package com.hunnit_beasts.kelog.post.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostViewCnt is a Querydsl query type for PostViewCnt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostViewCnt extends EntityPathBase<PostViewCnt> {

    private static final long serialVersionUID = 1191331419L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostViewCnt postViewCnt = new QPostViewCnt("postViewCnt");

    public final com.hunnit_beasts.kelog.postassist.entity.domain.compositekey.QPostViewCntId id;

    public final QPost post;

    public final NumberPath<Long> viewCnt = createNumber("viewCnt", Long.class);

    public QPostViewCnt(String variable) {
        this(PostViewCnt.class, forVariable(variable), INITS);
    }

    public QPostViewCnt(Path<? extends PostViewCnt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostViewCnt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostViewCnt(PathMetadata metadata, PathInits inits) {
        this(PostViewCnt.class, metadata, inits);
    }

    public QPostViewCnt(Class<? extends PostViewCnt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new com.hunnit_beasts.kelog.postassist.entity.domain.compositekey.QPostViewCntId(forProperty("id")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

