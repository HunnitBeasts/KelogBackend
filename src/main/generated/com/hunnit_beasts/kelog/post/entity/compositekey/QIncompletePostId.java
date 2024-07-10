package com.hunnit_beasts.kelog.post.entity.compositekey;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIncompletePostId is a Querydsl query type for IncompletePostId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QIncompletePostId extends BeanPath<IncompletePostId> {

    private static final long serialVersionUID = -1054299602L;

    public static final QIncompletePostId incompletePostId = new QIncompletePostId("incompletePostId");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QIncompletePostId(String variable) {
        super(IncompletePostId.class, forVariable(variable));
    }

    public QIncompletePostId(Path<? extends IncompletePostId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIncompletePostId(PathMetadata metadata) {
        super(IncompletePostId.class, metadata);
    }

}

