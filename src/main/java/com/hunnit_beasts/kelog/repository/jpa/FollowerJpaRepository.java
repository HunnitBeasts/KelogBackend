package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.compositekey.FollowerId;
import com.hunnit_beasts.kelog.entity.domain.Follower;
import org.springframework.data.repository.CrudRepository;

public interface FollowerJpaRepository extends CrudRepository<Follower, FollowerId> {

}
