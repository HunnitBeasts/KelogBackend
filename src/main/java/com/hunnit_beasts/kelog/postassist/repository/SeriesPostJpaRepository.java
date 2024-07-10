package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.entity.compositekey.SeriesPostId;
import com.hunnit_beasts.kelog.postassist.entity.domain.SeriesPost;
import org.springframework.data.repository.CrudRepository;

public interface SeriesPostJpaRepository extends CrudRepository<SeriesPost, SeriesPostId> {
}
