package com.hunnit_beasts.kelog.comment.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReComment is a Querydsl query type for ReComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReComment extends EntityPathBase<ReComment> {

    private static final long serialVersionUID = 1624726458L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReComment reComment = new QReComment("reComment");

    public final com.hunnit_beasts.kelog.common.entity.superclass.QRegEntity _super = new com.hunnit_beasts.kelog.common.entity.superclass.QRegEntity(this);

    public final QComment childComment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QComment parentComment;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QReComment(String variable) {
        this(ReComment.class, forVariable(variable), INITS);
    }

    public QReComment(Path<? extends ReComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReComment(PathMetadata metadata, PathInits inits) {
        this(ReComment.class, metadata, inits);
    }

    public QReComment(Class<? extends ReComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.childComment = inits.isInitialized("childComment") ? new QComment(forProperty("childComment"), inits.get("childComment")) : null;
        this.parentComment = inits.isInitialized("parentComment") ? new QComment(forProperty("parentComment"), inits.get("parentComment")) : null;
    }

}

