package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.request.post.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.post.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.dto.response.post.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.dto.response.post.PostLikeResponseDTO;
import com.hunnit_beasts.kelog.dto.response.post.PostViewCntResponseDTO;
import com.hunnit_beasts.kelog.entity.compositekey.LikedPostId;
import com.hunnit_beasts.kelog.entity.compositekey.PostViewCntId;
import com.hunnit_beasts.kelog.entity.domain.LikedPost;
import com.hunnit_beasts.kelog.entity.domain.Post;
import com.hunnit_beasts.kelog.entity.domain.PostViewCnt;
import com.hunnit_beasts.kelog.entity.domain.User;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.repository.jpa.LikedPostJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.PostViewCntJpaRepository;
import com.hunnit_beasts.kelog.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.repository.querydsl.PostQueryDSLRepository;
import com.hunnit_beasts.kelog.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final LikedPostJpaRepository likedPostJpaRepository;
    private final PostViewCntJpaRepository postViewCntJpaRepository;
    private final PostQueryDSLRepository postQueryDSLRepository;

    @Override
    public PostCreateResponseDTO postCreate(Long userId, PostCreateRequestDTO dto) {
        User creator = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getMessage()));
        Post createPostEntity = new Post(dto,creator);
        Post createdPost = postJpaRepository.save(createPostEntity);
        return postQueryDSLRepository.findPostCreateResponseDTOById(createdPost.getId());
    }

    @Override
    public Long postDelete(Long postId) {
        postJpaRepository.deleteById(postId);
        return postId;
    }

    @Override
    public PostLikeResponseDTO addPostLike(Long userId, PostLikeRequestDTO dto) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getMessage()));
        Post post = postJpaRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_POST_DATA_ERROR.getMessage()));

        if (likedPostJpaRepository.existsById(new LikedPostId(user, post)))
            throw new IllegalArgumentException(ErrorCode.POST_LIKE_DUPLICATION_ERROR.getMessage());
        else {
            LikedPost likedPost = likedPostJpaRepository.save(new LikedPost(user, post));
            return new PostLikeResponseDTO(likedPost);
        }
    }

    @Override
    public PostViewCntResponseDTO plusViewCnt(Long postId) {
        Post plusViewCntPost = postJpaRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_POST_DATA_ERROR.getMessage()));
        PostViewCntId thisPostsViewCnt = new PostViewCntId(plusViewCntPost.getId());
        if(postViewCntJpaRepository.existsById(thisPostsViewCnt)){
            PostViewCnt postViewCnt = postViewCntJpaRepository.findById(thisPostsViewCnt)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_POST_VIEW_DATA_ERROR.getMessage()));
            postViewCntJpaRepository.save(postViewCnt.plusViewCnt());
        }else
            postViewCntJpaRepository.save(new PostViewCnt(plusViewCntPost));
        return new PostViewCntResponseDTO(postQueryDSLRepository.findTotalViewCntById(postId));
    }
}
