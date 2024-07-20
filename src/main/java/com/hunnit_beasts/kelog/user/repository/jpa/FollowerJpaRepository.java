package com.hunnit_beasts.kelog.user.repository.jpa;

import com.hunnit_beasts.kelog.user.entity.compositekey.FollowerId;
import com.hunnit_beasts.kelog.user.entity.domain.Follower;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowerJpaRepository extends CrudRepository<Follower, FollowerId> {
    List<Follower> findByFollowee_Id(Long followeeId);
}
