package com.hunnit_beasts.kelog.post.repository.jpa;

import com.hunnit_beasts.kelog.post.entity.domain.LikedPost;
import com.hunnit_beasts.kelog.postassist.entity.domain.compositekey.LikedPostId;
import org.springframework.data.repository.CrudRepository;

public interface LikedPostJpaRepository extends CrudRepository<LikedPost, LikedPostId> {
}
