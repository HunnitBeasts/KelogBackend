package com.hunnit_beasts.kelog.comment.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = 274468077L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final com.hunnit_beasts.kelog.common.entity.superclass.QBaseEntity _super = new com.hunnit_beasts.kelog.common.entity.superclass.QBaseEntity(this);

    public final ListPath<ReComment, QReComment> childReComments = this.<ReComment, QReComment>createList("childReComments", ReComment.class, QReComment.class, PathInits.DIRECT2);

    public final QCommentContent commentContent;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final QReComment parentReComment;

    public final com.hunnit_beasts.kelog.post.entity.domain.QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final com.hunnit_beasts.kelog.user.entity.domain.QUser user;

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.commentContent = inits.isInitialized("commentContent") ? new QCommentContent(forProperty("commentContent"), inits.get("commentContent")) : null;
        this.parentReComment = inits.isInitialized("parentReComment") ? new QReComment(forProperty("parentReComment"), inits.get("parentReComment")) : null;
        this.post = inits.isInitialized("post") ? new com.hunnit_beasts.kelog.post.entity.domain.QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.hunnit_beasts.kelog.user.entity.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

