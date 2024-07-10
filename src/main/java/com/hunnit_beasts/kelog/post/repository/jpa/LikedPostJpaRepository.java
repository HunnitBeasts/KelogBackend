package com.hunnit_beasts.kelog.post.repository.jpa;

import com.hunnit_beasts.kelog.post.entity.compositekey.LikedPostId;
import com.hunnit_beasts.kelog.post.entity.domain.LikedPost;
import org.springframework.data.repository.CrudRepository;

public interface LikedPostJpaRepository extends CrudRepository<LikedPost, LikedPostId> {
}
