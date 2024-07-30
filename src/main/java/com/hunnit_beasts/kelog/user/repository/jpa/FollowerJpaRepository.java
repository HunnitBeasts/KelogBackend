package com.hunnit_beasts.kelog.user.repository.jpa;

import com.hunnit_beasts.kelog.user.entity.compositekey.FollowerId;
import com.hunnit_beasts.kelog.user.entity.domain.Follower;
import org.springframework.data.repository.CrudRepository;

public interface FollowerJpaRepository extends CrudRepository<Follower, FollowerId> {
}
