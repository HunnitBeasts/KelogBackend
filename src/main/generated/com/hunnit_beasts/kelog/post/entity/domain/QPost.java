package com.hunnit_beasts.kelog.post.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1047750167L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.hunnit_beasts.kelog.common.entity.superclass.QBaseEntity _super = new com.hunnit_beasts.kelog.common.entity.superclass.QBaseEntity(this);

    public final ListPath<com.hunnit_beasts.kelog.comment.entity.domain.Comment, com.hunnit_beasts.kelog.comment.entity.domain.QComment> comments = this.<com.hunnit_beasts.kelog.comment.entity.domain.Comment, com.hunnit_beasts.kelog.comment.entity.domain.QComment>createList("comments", com.hunnit_beasts.kelog.comment.entity.domain.Comment.class, com.hunnit_beasts.kelog.comment.entity.domain.QComment.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final ListPath<LikedPost, QLikedPost> likedPosts = this.<LikedPost, QLikedPost>createList("likedPosts", LikedPost.class, QLikedPost.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final QPostContent postContent;

    public final ListPath<PostViewCnt, QPostViewCnt> postViewCnts = this.<PostViewCnt, QPostViewCnt>createList("postViewCnts", PostViewCnt.class, QPostViewCnt.class, PathInits.DIRECT2);

    public final ListPath<RecentPost, QRecentPost> recentPosts = this.<RecentPost, QRecentPost>createList("recentPosts", RecentPost.class, QRecentPost.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final ListPath<com.hunnit_beasts.kelog.postassist.entity.domain.SeriesPost, com.hunnit_beasts.kelog.postassist.entity.domain.QSeriesPost> seriesPosts = this.<com.hunnit_beasts.kelog.postassist.entity.domain.SeriesPost, com.hunnit_beasts.kelog.postassist.entity.domain.QSeriesPost>createList("seriesPosts", com.hunnit_beasts.kelog.postassist.entity.domain.SeriesPost.class, com.hunnit_beasts.kelog.postassist.entity.domain.QSeriesPost.class, PathInits.DIRECT2);

    public final StringPath shortContent = createString("shortContent");

    public final ListPath<com.hunnit_beasts.kelog.postassist.entity.domain.TagPost, com.hunnit_beasts.kelog.postassist.entity.domain.QTagPost> tagPosts = this.<com.hunnit_beasts.kelog.postassist.entity.domain.TagPost, com.hunnit_beasts.kelog.postassist.entity.domain.QTagPost>createList("tagPosts", com.hunnit_beasts.kelog.postassist.entity.domain.TagPost.class, com.hunnit_beasts.kelog.postassist.entity.domain.QTagPost.class, PathInits.DIRECT2);

    public final StringPath thumbImage = createString("thumbImage");

    public final StringPath title = createString("title");

    public final EnumPath<com.hunnit_beasts.kelog.post.enumeration.PostType> type = createEnum("type", com.hunnit_beasts.kelog.post.enumeration.PostType.class);

    public final StringPath url = createString("url");

    public final com.hunnit_beasts.kelog.user.entity.domain.QUser user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postContent = inits.isInitialized("postContent") ? new QPostContent(forProperty("postContent"), inits.get("postContent")) : null;
        this.user = inits.isInitialized("user") ? new com.hunnit_beasts.kelog.user.entity.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

