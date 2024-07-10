package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.entity.domain.Series;
import org.springframework.data.repository.CrudRepository;

public interface SeriesJpaRepository extends CrudRepository<Series, Long> {
}
