package com.hunnit_beasts.kelog.postassist.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeriesPost is a Querydsl query type for SeriesPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeriesPost extends EntityPathBase<SeriesPost> {

    private static final long serialVersionUID = -1457787145L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeriesPost seriesPost = new QSeriesPost("seriesPost");

    public final com.hunnit_beasts.kelog.postassist.entity.compositekey.QSeriesPostId id;

    public final com.hunnit_beasts.kelog.post.entity.domain.QPost post;

    public final QSeries series;

    public final NumberPath<Long> seriesOrder = createNumber("seriesOrder", Long.class);

    public QSeriesPost(String variable) {
        this(SeriesPost.class, forVariable(variable), INITS);
    }

    public QSeriesPost(Path<? extends SeriesPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeriesPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeriesPost(PathMetadata metadata, PathInits inits) {
        this(SeriesPost.class, metadata, inits);
    }

    public QSeriesPost(Class<? extends SeriesPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new com.hunnit_beasts.kelog.postassist.entity.compositekey.QSeriesPostId(forProperty("id")) : null;
        this.post = inits.isInitialized("post") ? new com.hunnit_beasts.kelog.post.entity.domain.QPost(forProperty("post"), inits.get("post")) : null;
        this.series = inits.isInitialized("series") ? new QSeries(forProperty("series"), inits.get("series")) : null;
    }

}

