package com.hunnit_beasts.kelog.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeries is a Querydsl query type for Series
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeries extends EntityPathBase<Series> {

    private static final long serialVersionUID = -55072262L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeries series1 = new QSeries("series1");

    public final com.hunnit_beasts.kelog.entity.superclass.QBaseEntity _super = new com.hunnit_beasts.kelog.entity.superclass.QBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final ListPath<SeriesPost, QSeriesPost> series = this.<SeriesPost, QSeriesPost>createList("series", SeriesPost.class, QSeriesPost.class, PathInits.DIRECT2);

    public final StringPath seriesName = createString("seriesName");

    public final StringPath url = createString("url");

    public final QUser user;

    public QSeries(String variable) {
        this(Series.class, forVariable(variable), INITS);
    }

    public QSeries(Path<? extends Series> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeries(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeries(PathMetadata metadata, PathInits inits) {
        this(Series.class, metadata, inits);
    }

    public QSeries(Class<? extends Series> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

