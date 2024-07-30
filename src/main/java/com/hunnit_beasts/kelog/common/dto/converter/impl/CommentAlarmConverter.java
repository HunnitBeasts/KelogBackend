package com.hunnit_beasts.kelog.common.dto.converter.impl;

import com.hunnit_beasts.kelog.comment.entity.domain.Comment;
import com.hunnit_beasts.kelog.comment.repository.CommentJpaRepository;
import com.hunnit_beasts.kelog.common.dto.convert.AlarmCommentInfos;
import com.hunnit_beasts.kelog.common.dto.converter.factory.AlarmConverter;
import com.hunnit_beasts.kelog.common.dto.converter.util.LinkUtil;
import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentAlarmConverter implements AlarmConverter {

    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public AlarmReadResponseDTO convert(Alarm alarm) {

        Comment comment = commentJpaRepository.findById(alarm.getTargetId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_COMMENT_DATA_ERROR));
        Post post1 = postJpaRepository.findById(comment.getPost().getId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        String postTitle1 = post1.getTitle();
        Object detail = new AlarmCommentInfos(postTitle1,comment.getCommentContent().getContent());
        User sender = userJpaRepository.findById(post1.getUser().getId()).orElseThrow(()-> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        String link = LinkUtil.createLink(sender.getUserId(), post1.getUrl());

        return new AlarmReadResponseDTO(alarm,sender,link,detail);
    }
}
