package com.hunnit_beasts.kelog.post.repository.jpa;

import com.hunnit_beasts.kelog.post.entity.domain.LikedPost;
import org.springframework.data.repository.CrudRepository;

public interface LikedPostJpaRepository extends CrudRepository<LikedPost, Long> {
    LikedPost findByPost_IdAndUser_Id(Long postId, Long userId);
    boolean existsByPost_IdAndUser_Id(Long postId, Long userId);
}
