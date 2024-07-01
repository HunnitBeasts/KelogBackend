package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.domain.Series;
import org.springframework.data.repository.CrudRepository;

public interface SeriesJpaRepository extends CrudRepository<Series, Long> {
}
