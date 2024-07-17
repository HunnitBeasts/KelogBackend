package com.hunnit_beasts.kelog.user.repository.querydsl;

import com.hunnit_beasts.kelog.auth.dto.response.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.user.dto.convert.SocialInfos;
import com.hunnit_beasts.kelog.user.dto.response.SocialUpdateResponseDTO;
import com.hunnit_beasts.kelog.user.entity.domain.QSocial;
import com.hunnit_beasts.kelog.user.entity.domain.QUser;
import com.hunnit_beasts.kelog.user.entity.domain.QUserIntro;
import com.querydsl.core.types.Projections;
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
}
