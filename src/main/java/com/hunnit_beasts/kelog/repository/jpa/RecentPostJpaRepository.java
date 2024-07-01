package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.compositekey.RecentPostId;
import com.hunnit_beasts.kelog.entity.domain.RecentPost;
import org.springframework.data.repository.CrudRepository;

public interface RecentPostJpaRepository extends CrudRepository<RecentPost, RecentPostId> {
}
