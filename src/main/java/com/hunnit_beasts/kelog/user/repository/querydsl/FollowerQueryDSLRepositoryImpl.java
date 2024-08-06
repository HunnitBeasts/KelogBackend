package com.hunnit_beasts.kelog.user.repository.querydsl;

import com.hunnit_beasts.kelog.user.entity.domain.QFollower;
import com.hunnit_beasts.kelog.user.entity.domain.QUser;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowerQueryDSLRepositoryImpl implements FollowerQueryDSLRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findFollowerByFolloweeId(Long senderId) {
        QUser user = QUser.user;
        QFollower follower = QFollower.follower1;

        return jpaQueryFactory.select(
                        user)
                .from(follower)
                .join(follower.follower, user)
                .where(follower.followee.id.eq(senderId))
                .fetch();

    }
}
