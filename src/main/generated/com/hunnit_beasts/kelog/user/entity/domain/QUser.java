package com.hunnit_beasts.kelog.user.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -822297591L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.hunnit_beasts.kelog.common.entity.superclass.QBaseEntity _super = new com.hunnit_beasts.kelog.common.entity.superclass.QBaseEntity(this);

    public final BooleanPath alarmSetting = createBoolean("alarmSetting");

    public final ListPath<com.hunnit_beasts.kelog.common.entity.domain.Alarm, com.hunnit_beasts.kelog.common.entity.domain.QAlarm> alarmUsers = this.<com.hunnit_beasts.kelog.common.entity.domain.Alarm, com.hunnit_beasts.kelog.common.entity.domain.QAlarm>createList("alarmUsers", com.hunnit_beasts.kelog.common.entity.domain.Alarm.class, com.hunnit_beasts.kelog.common.entity.domain.QAlarm.class, PathInits.DIRECT2);

    public final StringPath briefIntro = createString("briefIntro");

    public final ListPath<com.hunnit_beasts.kelog.comment.entity.domain.Comment, com.hunnit_beasts.kelog.comment.entity.domain.QComment> comments = this.<com.hunnit_beasts.kelog.comment.entity.domain.Comment, com.hunnit_beasts.kelog.comment.entity.domain.QComment>createList("comments", com.hunnit_beasts.kelog.comment.entity.domain.Comment.class, com.hunnit_beasts.kelog.comment.entity.domain.QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final BooleanPath emailSetting = createBoolean("emailSetting");

    public final ListPath<Follower, QFollower> followees = this.<Follower, QFollower>createList("followees", Follower.class, QFollower.class, PathInits.DIRECT2);

    public final ListPath<Follower, QFollower> followers = this.<Follower, QFollower>createList("followers", Follower.class, QFollower.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath kelogName = createString("kelogName");

    public final ListPath<com.hunnit_beasts.kelog.post.entity.domain.LikedPost, com.hunnit_beasts.kelog.post.entity.domain.QLikedPost> LikedPostUsers = this.<com.hunnit_beasts.kelog.post.entity.domain.LikedPost, com.hunnit_beasts.kelog.post.entity.domain.QLikedPost>createList("LikedPostUsers", com.hunnit_beasts.kelog.post.entity.domain.LikedPost.class, com.hunnit_beasts.kelog.post.entity.domain.QLikedPost.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.hunnit_beasts.kelog.post.entity.domain.Post, com.hunnit_beasts.kelog.post.entity.domain.QPost> posts = this.<com.hunnit_beasts.kelog.post.entity.domain.Post, com.hunnit_beasts.kelog.post.entity.domain.QPost>createList("posts", com.hunnit_beasts.kelog.post.entity.domain.Post.class, com.hunnit_beasts.kelog.post.entity.domain.QPost.class, PathInits.DIRECT2);

    public final ListPath<com.hunnit_beasts.kelog.post.entity.domain.RecentPost, com.hunnit_beasts.kelog.post.entity.domain.QRecentPost> recentPostUsers = this.<com.hunnit_beasts.kelog.post.entity.domain.RecentPost, com.hunnit_beasts.kelog.post.entity.domain.QRecentPost>createList("recentPostUsers", com.hunnit_beasts.kelog.post.entity.domain.RecentPost.class, com.hunnit_beasts.kelog.post.entity.domain.QRecentPost.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final ListPath<com.hunnit_beasts.kelog.postassist.entity.domain.Series, com.hunnit_beasts.kelog.postassist.entity.domain.QSeries> seriesList = this.<com.hunnit_beasts.kelog.postassist.entity.domain.Series, com.hunnit_beasts.kelog.postassist.entity.domain.QSeries>createList("seriesList", com.hunnit_beasts.kelog.postassist.entity.domain.Series.class, com.hunnit_beasts.kelog.postassist.entity.domain.QSeries.class, PathInits.DIRECT2);

    public final ListPath<SocialInfo, QSocialInfo> socialInfos = this.<SocialInfo, QSocialInfo>createList("socialInfos", SocialInfo.class, QSocialInfo.class, PathInits.DIRECT2);

    public final ListPath<com.hunnit_beasts.kelog.common.entity.domain.Alarm, com.hunnit_beasts.kelog.common.entity.domain.QAlarm> targets = this.<com.hunnit_beasts.kelog.common.entity.domain.Alarm, com.hunnit_beasts.kelog.common.entity.domain.QAlarm>createList("targets", com.hunnit_beasts.kelog.common.entity.domain.Alarm.class, com.hunnit_beasts.kelog.common.entity.domain.QAlarm.class, PathInits.DIRECT2);

    public final StringPath thumbImage = createString("thumbImage");

    public final StringPath userId = createString("userId");

    public final QUserIntro userIntro;

    public final EnumPath<com.hunnit_beasts.kelog.user.enumeration.UserType> userType = createEnum("userType", com.hunnit_beasts.kelog.user.enumeration.UserType.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userIntro = inits.isInitialized("userIntro") ? new QUserIntro(forProperty("userIntro"), inits.get("userIntro")) : null;
    }

}

