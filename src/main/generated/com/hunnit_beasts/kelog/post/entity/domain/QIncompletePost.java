package com.hunnit_beasts.kelog.post.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIncompletePost is a Querydsl query type for IncompletePost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIncompletePost extends EntityPathBase<IncompletePost> {

    private static final long serialVersionUID = 1427386663L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIncompletePost incompletePost = new QIncompletePost("incompletePost");

    public final com.hunnit_beasts.kelog.postassist.entity.domain.compositekey.QIncompletePostId id;

    public final QPost post;

    public final com.hunnit_beasts.kelog.user.entity.domain.QUser user;

    public QIncompletePost(String variable) {
        this(IncompletePost.class, forVariable(variable), INITS);
    }

    public QIncompletePost(Path<? extends IncompletePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIncompletePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIncompletePost(PathMetadata metadata, PathInits inits) {
        this(IncompletePost.class, metadata, inits);
    }

    public QIncompletePost(Class<? extends IncompletePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new com.hunnit_beasts.kelog.postassist.entity.domain.compositekey.QIncompletePostId(forProperty("id")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.hunnit_beasts.kelog.user.entity.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

