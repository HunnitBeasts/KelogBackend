package com.hunnit_beasts.kelog.common.entity.superclass;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegEntity is a Querydsl query type for RegEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QRegEntity extends EntityPathBase<RegEntity> {

    private static final long serialVersionUID = 148153874L;

    public static final QRegEntity regEntity = new QRegEntity("regEntity");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public QRegEntity(String variable) {
        super(RegEntity.class, forVariable(variable));
    }

    public QRegEntity(Path<? extends RegEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegEntity(PathMetadata metadata) {
        super(RegEntity.class, metadata);
    }

}

