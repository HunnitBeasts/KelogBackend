package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.request.comment.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.comment.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.entity.domain.Comment;
import com.hunnit_beasts.kelog.entity.domain.Post;
import com.hunnit_beasts.kelog.entity.domain.User;
import com.hunnit_beasts.kelog.repository.jpa.CommentJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.repository.querydsl.CommentQueryDSLRepository;
import com.hunnit_beasts.kelog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.hunnit_beasts.kelog.enumeration.system.ErrorCode.NO_POST_DATA_ERROR;
import static com.hunnit_beasts.kelog.enumeration.system.ErrorCode.NO_USER_DATA_ERROR;

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
                .orElseThrow(()-> new IllegalArgumentException(NO_USER_DATA_ERROR.getCode()));
        Post commentedPost = postJpaRepository.findById(dto.getPostId())
                .orElseThrow(()-> new IllegalArgumentException(NO_POST_DATA_ERROR.getCode()));
        Comment createdCommentEntity = new Comment(dto,commentedPost,commentWriter);
        Comment createdComment = commentJpaRepository.save(createdCommentEntity);
        return commentQueryDSLRepository.findCommentCreateResponseDTOById(createdComment.getId());
    }
}
