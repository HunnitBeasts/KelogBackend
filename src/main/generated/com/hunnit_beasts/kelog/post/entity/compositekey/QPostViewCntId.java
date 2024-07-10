package com.hunnit_beasts.kelog.post.entity.compositekey;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostViewCntId is a Querydsl query type for PostViewCntId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPostViewCntId extends BeanPath<PostViewCntId> {

    private static final long serialVersionUID = 719442634L;

    public static final QPostViewCntId postViewCntId = new QPostViewCntId("postViewCntId");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public QPostViewCntId(String variable) {
        super(PostViewCntId.class, forVariable(variable));
    }

    public QPostViewCntId(Path<? extends PostViewCntId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostViewCntId(PathMetadata metadata) {
        super(PostViewCntId.class, metadata);
    }

}

