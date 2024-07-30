package com.hunnit_beasts.kelog.common.repository.jpa;

import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlarmJpaRepository extends CrudRepository<Alarm, Long> {
    boolean existsByUser_IdAndTargetIdAndAlarmType(Long userId, Long receiverId, AlarmType alarmType);
    List<Alarm> findByUser_Id(Long userId);
}
