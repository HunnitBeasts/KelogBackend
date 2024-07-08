package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.compositekey.SeriesPostId;
import com.hunnit_beasts.kelog.entity.domain.SeriesPost;
import org.springframework.data.repository.CrudRepository;

public interface SeriesPostJpaRepository extends CrudRepository<SeriesPost, SeriesPostId> {
}
