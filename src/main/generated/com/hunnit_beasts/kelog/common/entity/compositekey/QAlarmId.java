package com.hunnit_beasts.kelog.common.entity.compositekey;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlarmId is a Querydsl query type for AlarmId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAlarmId extends BeanPath<AlarmId> {

    private static final long serialVersionUID = 51386402L;

    public static final QAlarmId alarmId = new QAlarmId("alarmId");

    public final EnumPath<com.hunnit_beasts.kelog.common.enumeration.AlarmType> alarmType = createEnum("alarmType", com.hunnit_beasts.kelog.common.enumeration.AlarmType.class);

    public final NumberPath<Long> targetId = createNumber("targetId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QAlarmId(String variable) {
        super(AlarmId.class, forVariable(variable));
    }

    public QAlarmId(Path<? extends AlarmId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlarmId(PathMetadata metadata) {
        super(AlarmId.class, metadata);
    }

}

