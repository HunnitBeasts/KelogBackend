package com.hunnit_beasts.kelog.user.repository.querydsl;

import com.hunnit_beasts.kelog.auth.dto.response.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.user.dto.convert.FollowerInfos;
import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.response.SocialUpdateResponseDTO;
import com.hunnit_beasts.kelog.user.entity.domain.QFollower;
import com.hunnit_beasts.kelog.user.entity.domain.QSocial;
import com.hunnit_beasts.kelog.user.entity.domain.QUser;
import com.hunnit_beasts.kelog.user.entity.domain.QUserIntro;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQueryDSLRepositoryImpl implements UserQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserCreateResponseDTO findUserCreateResponseDTOById(Long id) {
        QUser user = QUser.user;
        QUserIntro userIntro = QUserIntro.userIntro;

        return jpaQueryFactory
                .select(Projections.constructor(UserCreateResponseDTO.class,
                        user.id,
                        user.userId,
                        user.nickname,
                        user.thumbImage,
                        user.briefIntro,
                        user.email,
                        user.emailSetting,
                        user.alarmSetting,
                        user.userType,
                        user.kelogName,
                        userIntro.intro,
                        user.regDate,
                        user.modDate))
                .from(user)
                .join(userIntro)
                .on(user.id.eq(userIntro.id))
                .where(user.id.eq(id))
                .fetchOne();
    }

    @Override
    public SocialUpdateResponseDTO findUserSocialsById(Long id) {
        QSocial social = QSocial.social;
        List<SocialInfos> socialInfos = jpaQueryFactory
                .select(Projections.constructor(SocialInfos.class,
                        social.link,
                        social.id.socialType))
                .from(social)
                .where(social.id.userId.eq(id))
                .fetch();
        return new SocialUpdateResponseDTO(id,socialInfos);
    }

    @Override
    public boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId) {
        if (followerId == null) return false;

        QFollower follow = QFollower.follower1;
        return jpaQueryFactory
                .selectOne()
                .from(follow)
                .where(follow.follower.id.eq(followerId)
                        .and(follow.followee.id.eq(followeeId)))
                .fetchFirst() != null;
    }

    @Override
    public List<FollowerInfos> findFollowerInfosByUserId(Long userId) {
        QFollower follow = QFollower.follower1;
        QFollower selfFollow = new QFollower("selfFollow");
        return jpaQueryFactory
                .select(Projections.constructor(FollowerInfos.class,
                        follow.followee.thumbImage,
                        follow.followee.nickname,
                        follow.followee.userId,
                        follow.followee.briefIntro,
                        JPAExpressions.selectOne()
                                .from(selfFollow)
                                .where(selfFollow.follower.id.eq(userId)
                                        .and(selfFollow.followee.id.eq(follow.followee.id)))
                                .exists()))
                .from(follow)
                .where(follow.follower.id.eq(userId))
                .orderBy(follow.regDate.desc())
                .fetch();
    }

    @Override
    public List<FollowerInfos> findFolloweeInfosByUserId(Long userId) {
        QFollower follow = QFollower.follower1;
        QFollower selfFollow = new QFollower("selfFollow");
        return jpaQueryFactory
                .select(Projections.constructor(FollowerInfos.class,
                        follow.follower.thumbImage,
                        follow.follower.nickname,
                        follow.follower.userId,
                        follow.follower.briefIntro,
                        JPAExpressions.selectOne()
                                .from(selfFollow)
                                .where(selfFollow.follower.id.eq(userId)
                                        .and(selfFollow.followee.id.eq(follow.follower.id)))
                                .exists()))
                .from(follow)
                .where(follow.followee.id.eq(userId))
                .orderBy(follow.regDate.desc())
                .fetch();
    }

    @Override
    public Long followerCountByUserId(Long userId) {
        QFollower follow = QFollower.follower1;
        return jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.follower.id.eq(userId))
                .fetchOne();
    }

    @Override
    public Long followeeCountByUserId(Long userId) {
        QFollower follow = QFollower.follower1;
        return jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.followee.id.eq(userId))
                .fetchOne();
    }
}
