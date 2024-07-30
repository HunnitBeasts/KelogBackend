package com.hunnit_beasts.kelog.user.repository.querydsl;

import com.hunnit_beasts.kelog.user.entity.domain.User;

import java.util.List;

public interface FollowerQueryDSLRepository {

    List<User> findFollowerByFolloweeId(Long senderId);
}
