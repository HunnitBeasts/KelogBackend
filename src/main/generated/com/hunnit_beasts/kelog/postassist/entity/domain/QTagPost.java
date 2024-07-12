package com.hunnit_beasts.kelog.postassist.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTagPost is a Querydsl query type for TagPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTagPost extends EntityPathBase<TagPost> {

    private static final long serialVersionUID = -1771454438L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTagPost tagPost = new QTagPost("tagPost");

    public final com.hunnit_beasts.kelog.postassist.entity.compositekey.QTagPostId id;

    public final com.hunnit_beasts.kelog.post.entity.domain.QPost post;

    public final QTag tag;

    public QTagPost(String variable) {
        this(TagPost.class, forVariable(variable), INITS);
    }

    public QTagPost(Path<? extends TagPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTagPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTagPost(PathMetadata metadata, PathInits inits) {
        this(TagPost.class, metadata, inits);
    }

    public QTagPost(Class<? extends TagPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new com.hunnit_beasts.kelog.postassist.entity.compositekey.QTagPostId(forProperty("id")) : null;
        this.post = inits.isInitialized("post") ? new com.hunnit_beasts.kelog.post.entity.domain.QPost(forProperty("post"), inits.get("post")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

