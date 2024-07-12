package com.hunnit_beasts.kelog.comment.entity.compositekey;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReCommentId is a Querydsl query type for ReCommentId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReCommentId extends BeanPath<ReCommentId> {

    private static final long serialVersionUID = -1424481239L;

    public static final QReCommentId reCommentId = new QReCommentId("reCommentId");

    public final NumberPath<Long> childCommentId = createNumber("childCommentId", Long.class);

    public final NumberPath<Long> parentCommentId = createNumber("parentCommentId", Long.class);

    public QReCommentId(String variable) {
        super(ReCommentId.class, forVariable(variable));
    }

    public QReCommentId(Path<? extends ReCommentId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReCommentId(PathMetadata metadata) {
        super(ReCommentId.class, metadata);
    }

}

