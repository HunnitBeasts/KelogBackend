package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.compositekey.LikedPostId;
import com.hunnit_beasts.kelog.entity.domain.LikedPost;
import org.springframework.data.repository.CrudRepository;

public interface LikedPostJpaRepository extends CrudRepository<LikedPost, LikedPostId> {
}
