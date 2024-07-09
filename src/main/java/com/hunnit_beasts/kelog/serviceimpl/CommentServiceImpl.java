package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.request.comment.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.comment.CommentUpdateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentDeleteResponseDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentUpdateResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.Comment;
import com.hunnit_beasts.kelog.entity.domain.CommentContent;
import com.hunnit_beasts.kelog.entity.domain.Post;
import com.hunnit_beasts.kelog.entity.domain.User;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.repository.jpa.CommentContentJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.CommentJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.repository.querydsl.CommentQueryDSLRepository;
import com.hunnit_beasts.kelog.service.CommentService;
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
