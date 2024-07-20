package com.hunnit_beasts.kelog.common.repository;

import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import org.springframework.data.repository.CrudRepository;

public interface AlarmJpaRepository extends CrudRepository<Alarm, Long> {
//    List<Alarm> findByUser_IdAndTarget_IdAndAlarmType(Long user_id, Long target_id, AlarmType alarmType);
    boolean existsByUser_IdAndTarget_IdAndAlarmType(Long user_id, Long target_id, AlarmType alarmType);
}
