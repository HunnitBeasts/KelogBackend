package com.hunnit_beasts.kelog.post.entity.compositekey;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecentPostId is a Querydsl query type for RecentPostId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRecentPostId extends BeanPath<RecentPostId> {

    private static final long serialVersionUID = -1095620629L;

    public static final QRecentPostId recentPostId = new QRecentPostId("recentPostId");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QRecentPostId(String variable) {
        super(RecentPostId.class, forVariable(variable));
    }

    public QRecentPostId(Path<? extends RecentPostId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecentPostId(PathMetadata metadata) {
        super(RecentPostId.class, metadata);
    }

}

