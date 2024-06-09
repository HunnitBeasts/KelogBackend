package com.hunnit_beasts.kelog.entity.compositekey;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLikedPostId is a Querydsl query type for LikedPostId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLikedPostId extends BeanPath<LikedPostId> {

    private static final long serialVersionUID = -1857605447L;

    public static final QLikedPostId likedPostId = new QLikedPostId("likedPostId");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QLikedPostId(String variable) {
        super(LikedPostId.class, forVariable(variable));
    }

    public QLikedPostId(Path<? extends LikedPostId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLikedPostId(PathMetadata metadata) {
        super(LikedPostId.class, metadata);
    }

}

