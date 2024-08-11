package com.hunnit_beasts.kelog.common.repository.querydsl;

import java.util.List;

public interface AlarmQueryDslRepository {
    List<Long> updateAllAlarmCheck(Long userId);
}
