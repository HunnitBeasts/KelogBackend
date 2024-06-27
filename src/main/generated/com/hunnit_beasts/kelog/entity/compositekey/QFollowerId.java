package com.hunnit_beasts.kelog.entity.compositekey;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFollowerId is a Querydsl query type for FollowerId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFollowerId extends BeanPath<FollowerId> {

    private static final long serialVersionUID = 1351352040L;

    public static final QFollowerId followerId = new QFollowerId("followerId");

    public final NumberPath<Long> followee = createNumber("followee", Long.class);

    public final NumberPath<Long> follower = createNumber("follower", Long.class);

    public QFollowerId(String variable) {
        super(FollowerId.class, forVariable(variable));
    }

    public QFollowerId(Path<? extends FollowerId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFollowerId(PathMetadata metadata) {
        super(FollowerId.class, metadata);
    }

}

