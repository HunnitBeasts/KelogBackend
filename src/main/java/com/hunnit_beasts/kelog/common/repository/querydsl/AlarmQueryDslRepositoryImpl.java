package com.hunnit_beasts.kelog.common.repository.querydsl;

import com.hunnit_beasts.kelog.comment.entity.domain.QComment;
import com.hunnit_beasts.kelog.common.dto.convert.AlarmCommentInfos;
import com.hunnit_beasts.kelog.common.dto.convert.AlarmFollowInfos;
import com.hunnit_beasts.kelog.common.dto.convert.AlarmLikeInfos;
import com.hunnit_beasts.kelog.common.dto.convert.AlarmPostInfos;
import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;
import com.hunnit_beasts.kelog.post.entity.domain.QPost;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AlarmQueryDslRepositoryImpl implements AlarmQueryDslRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AlarmReadResponseDTO findLikeAlarmReadResponseDTOById(Long alarmId) {

//        QAlarm alarm = QAlarm.alarm;
//        QPost post = QPost.post;
//
//        jpaQueryFactory.select(Projections.constructor(AlarmReadResponseDTO.class,
//                alarm.id,
//                alarm.alarmType,
//                post.user.thumbImage,
//                post.user.nickname,
//                alarm.isCheck,
//                post.url,
//                alarm.regDate))
//                .from(alarm)
//                .join(post)
//                .on()


        return null;
    }

    @Override
    public AlarmReadResponseDTO findPostAlarmReadResponseDTOById(Long alarmId) {
        return null;
    }

    @Override
    public AlarmReadResponseDTO findCommentAlarmReadResponseDTOById(Long alarmId) {
        return null;
    }

    @Override
    public AlarmReadResponseDTO findFollowAlarmReadResponseDTOById(Long alarmId) {
        return null;
    }

    @Override
    public AlarmCommentInfos findAlarmCommentInfosById(Long commentId) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;

        return jpaQueryFactory.select(Projections.constructor(AlarmCommentInfos.class,
                    post.title,
                    comment.commentContent.content))
                .from(comment)
                .join(post)
                .on(comment.post.id.eq(post.id))
                .where(comment.id.eq(commentId))
                .fetchOne();
    }

    @Override
    public AlarmFollowInfos findAlarmFollowInfosById(Long alarmId) {
        return null;
    }

    @Override
    public AlarmLikeInfos findAlarmLikeInfosById(Long postId) {
        QPost post = QPost.post;

        return jpaQueryFactory.select(Projections.constructor(AlarmLikeInfos.class,
                    post.title))
                .from(post)
                .where(post.id.eq(postId))
                .fetchOne();
    }

    @Override
    public AlarmPostInfos findAlarmPostInfosById(Long postId) {
        QPost post = QPost.post;

        return jpaQueryFactory.select(Projections.constructor(AlarmPostInfos.class,
                        post.title))
                .from(post)
                .where(post.id.eq(postId))
                .fetchOne();
    }
}
