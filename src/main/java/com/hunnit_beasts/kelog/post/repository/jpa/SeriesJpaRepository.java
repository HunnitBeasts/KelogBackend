package com.hunnit_beasts.kelog.post.repository.jpa;

import com.hunnit_beasts.kelog.post.entity.domain.Series;
import org.springframework.data.repository.CrudRepository;

public interface SeriesJpaRepository extends CrudRepository<Series, Long> {
}
