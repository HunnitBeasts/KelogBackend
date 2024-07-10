package com.hunnit_beasts.kelog.post.entity.compositekey;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTagPostId is a Querydsl query type for TagPostId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTagPostId extends BeanPath<TagPostId> {

    private static final long serialVersionUID = -175950240L;

    public static final QTagPostId tagPostId = new QTagPostId("tagPostId");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final StringPath tagName = createString("tagName");

    public QTagPostId(String variable) {
        super(TagPostId.class, forVariable(variable));
    }

    public QTagPostId(Path<? extends TagPostId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTagPostId(PathMetadata metadata) {
        super(TagPostId.class, metadata);
    }

}

