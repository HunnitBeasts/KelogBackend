package com.hunnit_beasts.kelog.post.repository.jpa;

import com.hunnit_beasts.kelog.postassist.entity.domain.compositekey.RecentPostId;
import com.hunnit_beasts.kelog.post.entity.domain.RecentPost;
import org.springframework.data.repository.CrudRepository;

public interface RecentPostJpaRepository extends CrudRepository<RecentPost, RecentPostId> {
}
