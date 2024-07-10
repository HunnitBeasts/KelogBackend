package com.hunnit_beasts.kelog.post.repository.jpa;

import com.hunnit_beasts.kelog.post.entity.compositekey.SeriesPostId;
import com.hunnit_beasts.kelog.post.entity.domain.SeriesPost;
import org.springframework.data.repository.CrudRepository;

public interface SeriesPostJpaRepository extends CrudRepository<SeriesPost, SeriesPostId> {
}
