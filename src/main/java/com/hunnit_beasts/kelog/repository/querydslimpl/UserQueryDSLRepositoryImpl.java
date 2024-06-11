package com.hunnit_beasts.kelog.repository.querydslimpl;

import com.hunnit_beasts.kelog.dto.response.user.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.QUser;
import com.hunnit_beasts.kelog.entity.domain.QUserIntro;
import com.hunnit_beasts.kelog.repository.querydsl.UserQueryDSLRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
