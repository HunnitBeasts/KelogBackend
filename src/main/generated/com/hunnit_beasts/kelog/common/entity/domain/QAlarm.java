package com.hunnit_beasts.kelog.common.entity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarm is a Querydsl query type for Alarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarm extends EntityPathBase<Alarm> {

    private static final long serialVersionUID = 1210695411L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarm alarm = new QAlarm("alarm");

    public final com.hunnit_beasts.kelog.common.entity.superclass.QRegEntity _super = new com.hunnit_beasts.kelog.common.entity.superclass.QRegEntity(this);

    public final EnumPath<com.hunnit_beasts.kelog.common.enumeration.AlarmType> alarmType = createEnum("alarmType", com.hunnit_beasts.kelog.common.enumeration.AlarmType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isCheck = createBoolean("isCheck");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final com.hunnit_beasts.kelog.user.entity.domain.QUser target;

    public final com.hunnit_beasts.kelog.user.entity.domain.QUser user;

    public QAlarm(String variable) {
        this(Alarm.class, forVariable(variable), INITS);
    }

    public QAlarm(Path<? extends Alarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarm(PathMetadata metadata, PathInits inits) {
        this(Alarm.class, metadata, inits);
    }

    public QAlarm(Class<? extends Alarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.target = inits.isInitialized("target") ? new com.hunnit_beasts.kelog.user.entity.domain.QUser(forProperty("target"), inits.get("target")) : null;
        this.user = inits.isInitialized("user") ? new com.hunnit_beasts.kelog.user.entity.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

