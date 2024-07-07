package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.entity.domain.Comment;
import com.hunnit_beasts.kelog.entity.domain.Post;
import com.hunnit_beasts.kelog.entity.domain.Series;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.repository.jpa.CommentJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.SeriesJpaRepository;
import com.hunnit_beasts.kelog.service.ValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class ValidateServiceImpl implements ValidateService {

    private final PostJpaRepository postJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final SeriesJpaRepository seriesJpaRepository;

    @Override
    public void userIdAndUserIdSameCheck(Long id, Long userId) {
        if(!Objects.equals(id, userId))
            throw new IllegalArgumentException(ErrorCode.NOT_SAME_USERID_ERROR.getCode());
    }

    @Override
    public void userIdAndPostIdSameCheck(Long id, Long postId) {
        Post userPost = postJpaRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_POST_DATA_ERROR.getCode()));
        if(!userPost.getUser().getId().equals(id))
            throw new IllegalArgumentException(ErrorCode.NOT_SAME_POST_ID_ERROR.getCode());
    }

    @Override
    public void userIdAndCommentIdSameCheck(Long id, Long commentId) {
        Comment comment = commentJpaRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_COMMENT_DATA_ERROR.getCode()));
        if(!comment.getUser().getId().equals(id))
            throw new IllegalArgumentException(ErrorCode.NOT_SAME_COMMENT_ID_ERROR.getCode());
    }

    @Override
    public void userIdAndSeriesIdSameCheck(Long id, Long seriesId) {
        Series series = seriesJpaRepository.findById(seriesId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_SERIES_DATA_ERROR.getCode()));
        if(!series.getUser().getId().equals(id))
            throw new IllegalArgumentException(ErrorCode.NOT_SAME_SERIES_ID_ERROR.getCode());
    }
}
