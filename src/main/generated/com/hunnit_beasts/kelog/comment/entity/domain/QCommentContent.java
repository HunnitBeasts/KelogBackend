package com.hunnit_beasts.kelog.comment.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentContent is a Querydsl query type for CommentContent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentContent extends EntityPathBase<CommentContent> {

    private static final long serialVersionUID = 1705298636L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentContent commentContent = new QCommentContent("commentContent");

    public final QComment comment;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCommentContent(String variable) {
        this(CommentContent.class, forVariable(variable), INITS);
    }

    public QCommentContent(Path<? extends CommentContent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentContent(PathMetadata metadata, PathInits inits) {
        this(CommentContent.class, metadata, inits);
    }

    public QCommentContent(Class<? extends CommentContent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment"), inits.get("comment")) : null;
    }

}

