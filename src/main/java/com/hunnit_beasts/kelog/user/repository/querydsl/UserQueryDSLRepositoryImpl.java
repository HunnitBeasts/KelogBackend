package com.hunnit_beasts.kelog.user.repository.querydsl;

import com.hunnit_beasts.kelog.auth.dto.response.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.user.dto.convert.FollowerInfos;
import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.convert.UserInfo;
import com.hunnit_beasts.kelog.user.dto.convert.UserMyInfo;
import com.hunnit_beasts.kelog.user.dto.response.SocialUpdateResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.UserInfoReadResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.UserMyInfoReadResponseDTO;
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
import java.util.Objects;

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

        return new SocialUpdateResponseDTO(id,createSocials(id));
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

    @Override
    public UserMyInfoReadResponseDTO findUserMyInfoReadResponseDTO(Long userId) {
        QUser user = QUser.user;

        UserMyInfo myInfo = jpaQueryFactory
                .select(Projections.constructor(UserMyInfo.class,
                        user.nickname,
                        user.thumbImage,
                        user.briefIntro,
                        user.userIntro.intro,
                        user.email,
                        user.emailSetting,
                        user.alarmSetting,
                        user.kelogName,
                        user.alarmUsers.size().count()))
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();

        return new UserMyInfoReadResponseDTO(Objects.requireNonNull(myInfo), createSocials(userId));
    }

    @Override
    public UserInfoReadResponseDTO findUserInfoReadResponseDTO(Long userId) {

        return new UserInfoReadResponseDTO(Objects.requireNonNull(createUserInfo(userId)), createSocials(userId));
    }

    @Override
    public UserInfoReadResponseDTO findUserInfoReadResponseDTO(Long userId, Long currentUserId) {
        QFollower follower = QFollower.follower1;

        Boolean followCheck = jpaQueryFactory
                .select(JPAExpressions
                        .selectOne()
                        .from(follower)
                        .where(follower.id.follower.eq(currentUserId)
                                .and(follower.id.followee.eq(userId)))
                        .exists())
                .from(follower)
                .fetchOne();

        return new UserInfoReadResponseDTO(
                Objects.requireNonNull(createUserInfo(userId)),
                createSocials(userId),
                followCheck != null ? followCheck : false);
    }

    private List<SocialInfos> createSocials(Long userId){
        QSocial social = QSocial.social;

        return jpaQueryFactory
                .select(Projections.constructor(SocialInfos.class,
                        social.link,
                        social.id.socialType))
                .from(social)
                .where(social.id.userId.eq(userId))
                .fetch();
    }

    private UserInfo createUserInfo(Long userId){
        QUser user = QUser.user;
        QFollower follower = QFollower.follower1;

        return jpaQueryFactory
                .select(Projections.constructor(UserInfo.class,
                        user.nickname,
                        user.thumbImage,
                        user.briefIntro,
                        user.kelogName,
                        JPAExpressions.select(follower.id.follower.count())
                                .from(follower)
                                .where(follower.id.followee.eq(userId)),   // 팔로워 수
                        JPAExpressions.select(follower.id.followee.count())
                                .from(follower)
                                .where(follower.id.follower.eq(userId))))  // 팔로잉 수
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();
    }

}
