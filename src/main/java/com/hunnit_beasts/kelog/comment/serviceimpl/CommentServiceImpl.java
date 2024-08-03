package com.hunnit_beasts.kelog.comment.serviceimpl;

import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.comment.dto.request.CommentUpdateRequestDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentDeleteResponseDTO;
import com.hunnit_beasts.kelog.comment.dto.response.CommentUpdateResponseDTO;
import com.hunnit_beasts.kelog.comment.entity.domain.Comment;
import com.hunnit_beasts.kelog.comment.entity.domain.CommentContent;
import com.hunnit_beasts.kelog.comment.repository.CommentContentJpaRepository;
import com.hunnit_beasts.kelog.comment.repository.CommentJpaRepository;
import com.hunnit_beasts.kelog.comment.repository.CommentQueryDSLRepository;
import com.hunnit_beasts.kelog.comment.service.CommentService;
import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.common.service.AlarmService;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final CommentContentJpaRepository commentContentJpaRepository;

    private final CommentQueryDSLRepository commentQueryDSLRepository;

    private final AlarmService alarmService;

    @Override
    public CommentCreateResponseDTO commentCreate(Long userId, CommentCreateRequestDTO dto) {
        User commentWriter = userJpaRepository.findById(userId)
                .orElseThrow(()-> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        Post commentedPost = postJpaRepository.findById(dto.getPostId())
                .orElseThrow(()-> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        Comment createdCommentEntity = new Comment(dto,commentedPost,commentWriter);
        Comment createdComment = commentJpaRepository.save(createdCommentEntity);

        return commentQueryDSLRepository.findCommentCreateResponseDTOById(createdComment.getId());
    }

    @Override
    public CommentDeleteResponseDTO commentDelete(Long commentId) {
        commentJpaRepository.deleteById(commentId);
        return new CommentDeleteResponseDTO(commentId);
    }

    @Override
    public CommentUpdateResponseDTO commentUpdate(Long commentId, CommentUpdateRequestDTO dto) {
        CommentContent commentContent = commentContentJpaRepository.findById(commentId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_COMMENT_DATA_ERROR));
        commentContentJpaRepository.save(commentContent.commentContentUpdate(dto));
        return commentQueryDSLRepository.findCommentUpdateResponseDTOById(commentId);
    }
}
