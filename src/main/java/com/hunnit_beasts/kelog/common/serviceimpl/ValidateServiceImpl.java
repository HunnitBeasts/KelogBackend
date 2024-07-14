package com.hunnit_beasts.kelog.common.serviceimpl;

import com.hunnit_beasts.kelog.comment.entity.domain.Comment;
import com.hunnit_beasts.kelog.comment.repository.CommentJpaRepository;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.common.service.ValidateService;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.postassist.entity.domain.Series;
import com.hunnit_beasts.kelog.postassist.repository.SeriesJpaRepository;
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
            throw new ExpectException(ErrorCode.NOT_SAME_USERID_ERROR);
    }

    @Override
    public void userIdAndPostIdSameCheck(Long id, Long postId) {
        Post userPost = postJpaRepository.findById(postId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        if(!userPost.getUser().getId().equals(id))
            throw new ExpectException(ErrorCode.NOT_SAME_POST_ID_ERROR);
    }

    @Override
    public void userIdAndCommentIdSameCheck(Long id, Long commentId) {
        Comment comment = commentJpaRepository.findById(commentId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_COMMENT_DATA_ERROR));
        if(!comment.getUser().getId().equals(id))
            throw new ExpectException(ErrorCode.NOT_SAME_COMMENT_ID_ERROR);
    }

    @Override
    public void userIdAndSeriesIdSameCheck(Long id, Long seriesId) {
        Series series = seriesJpaRepository.findById(seriesId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_SERIES_DATA_ERROR));
        if(!series.getUser().getId().equals(id))
            throw new ExpectException(ErrorCode.NOT_SAME_SERIES_ID_ERROR);
    }
}
