package com.hunnit_beasts.kelog.common.repository.querydsl;

import com.hunnit_beasts.kelog.common.entity.domain.QAlarm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlarmQueryDslRepositoryImpl implements AlarmQueryDslRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Override
    public List<Long> updateAllAlarmCheck(Long userId) {

        QAlarm alarm = QAlarm.alarm;

        List<Long> uncheckedAlarmIds = jpaQueryFactory
                .select(alarm.id)
                .from(alarm)
                .where(alarm.user.id.eq(userId)
                        .and(alarm.isCheck.eq(false)))
                .fetch();

        if (!uncheckedAlarmIds.isEmpty()) {
            jpaQueryFactory
                    .update(alarm)
                    .set(alarm.isCheck, true)
                    .where(alarm.id.in(uncheckedAlarmIds))
                    .execute();
        }

        entityManager.flush();
        entityManager.clear();

        return uncheckedAlarmIds;
    }
}
