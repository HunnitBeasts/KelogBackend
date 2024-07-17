package com.hunnit_beasts.kelog.common.repository;

import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import org.springframework.data.repository.CrudRepository;

public interface AlarmJpaRepository extends CrudRepository<Alarm, Long> {
}
