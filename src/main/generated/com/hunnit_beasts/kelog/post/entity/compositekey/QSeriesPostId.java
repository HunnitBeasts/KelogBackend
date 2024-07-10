package com.hunnit_beasts.kelog.post.entity.compositekey;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeriesPostId is a Querydsl query type for SeriesPostId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSeriesPostId extends BeanPath<SeriesPostId> {

    private static final long serialVersionUID = -1947051257L;

    public static final QSeriesPostId seriesPostId = new QSeriesPostId("seriesPostId");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final NumberPath<Long> seriesId = createNumber("seriesId", Long.class);

    public QSeriesPostId(String variable) {
        super(SeriesPostId.class, forVariable(variable));
    }

    public QSeriesPostId(Path<? extends SeriesPostId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeriesPostId(PathMetadata metadata) {
        super(SeriesPostId.class, metadata);
    }

}

