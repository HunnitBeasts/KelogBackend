package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.request.comment.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.Comment;
import com.hunnit_beasts.kelog.entity.domain.Post;
import com.hunnit_beasts.kelog.entity.domain.User;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
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
    private final CommentQueryDSLRepository commentQueryDSLRepository;

    @Override
    public CommentCreateResponseDTO commentCreate(Long userId, CommentCreateRequestDTO dto) {
        User commentWriter = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getMessage()));
        Post commentedPost = postJpaRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_POST_DATA_ERROR.getMessage()));
        Comment createdCommentEntity = new Comment(dto,commentedPost,commentWriter);
        Comment createdComment = commentJpaRepository.save(createdCommentEntity);
        return commentQueryDSLRepository.findCommentCreateResponseDTOById(createdComment.getId());
    }
}
