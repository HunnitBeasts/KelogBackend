package com.hunnit_beasts.kelog.entity.domain;

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

    private static final long serialVersionUID = -1202217970L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.hunnit_beasts.kelog.entity.superclass.QBaseEntity _super = new com.hunnit_beasts.kelog.entity.superclass.QBaseEntity(this);

    public final BooleanPath alarmSetting = createBoolean("alarmSetting");

    public final ListPath<Alarm, QAlarm> alarmUsers = this.<Alarm, QAlarm>createList("alarmUsers", Alarm.class, QAlarm.class, PathInits.DIRECT2);

    public final StringPath briefIntro = createString("briefIntro");

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final BooleanPath emailSetting = createBoolean("emailSetting");

    public final ListPath<Follower, QFollower> followeds = this.<Follower, QFollower>createList("followeds", Follower.class, QFollower.class, PathInits.DIRECT2);

    public final ListPath<Follower, QFollower> followings = this.<Follower, QFollower>createList("followings", Follower.class, QFollower.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<IncompletePost, QIncompletePost> incompletePostUsers = this.<IncompletePost, QIncompletePost>createList("incompletePostUsers", IncompletePost.class, QIncompletePost.class, PathInits.DIRECT2);

    public final StringPath kelogName = createString("kelogName");

    public final ListPath<LikedPost, QLikedPost> LikedPostUsers = this.<LikedPost, QLikedPost>createList("LikedPostUsers", LikedPost.class, QLikedPost.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<Post, QPost> posts = this.<Post, QPost>createList("posts", Post.class, QPost.class, PathInits.DIRECT2);

    public final ListPath<RecentPost, QRecentPost> recentPostUsers = this.<RecentPost, QRecentPost>createList("recentPostUsers", RecentPost.class, QRecentPost.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final ListPath<Series, QSeries> seriesList = this.<Series, QSeries>createList("seriesList", Series.class, QSeries.class, PathInits.DIRECT2);

    public final ListPath<SocialInfo, QSocialInfo> socialInfos = this.<SocialInfo, QSocialInfo>createList("socialInfos", SocialInfo.class, QSocialInfo.class, PathInits.DIRECT2);

    public final ListPath<Alarm, QAlarm> targets = this.<Alarm, QAlarm>createList("targets", Alarm.class, QAlarm.class, PathInits.DIRECT2);

    public final StringPath thumbImage = createString("thumbImage");

    public final StringPath userId = createString("userId");

    public final QUserIntro userIntro;

    public final EnumPath<com.hunnit_beasts.kelog.enumeration.types.UserType> userType = createEnum("userType", com.hunnit_beasts.kelog.enumeration.types.UserType.class);

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

