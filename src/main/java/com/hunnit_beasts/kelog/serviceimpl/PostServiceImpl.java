package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.request.post.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.request.post.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.dto.request.post.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.post.*;
import com.hunnit_beasts.kelog.entity.compositekey.LikedPostId;
import com.hunnit_beasts.kelog.entity.compositekey.PostViewCntId;
import com.hunnit_beasts.kelog.entity.compositekey.RecentPostId;
import com.hunnit_beasts.kelog.entity.domain.*;
import com.hunnit_beasts.kelog.enumeration.system.ErrorCode;
import com.hunnit_beasts.kelog.repository.jpa.*;
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
    private final SeriesJpaRepository seriesJpaRepository;

    private final PostQueryDSLRepository postQueryDSLRepository;
    private final RecentPostJpaRepository recentPostJpaRepository;

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

    @Override
    public PostLikeResponseDTO deletePostLike(Long userId, Long postId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getMessage()));
        Post post = postJpaRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_POST_DATA_ERROR.getMessage()));
        LikedPostId likedPostId = new LikedPostId(user, post);
        if (likedPostJpaRepository.existsById(likedPostId))
            likedPostJpaRepository.deleteById(likedPostId);
        else
            throw new IllegalArgumentException(ErrorCode.POST_LIKE_DUPLICATION_ERROR.getMessage());
        return new PostLikeResponseDTO(userId,postId);  
    }
  
    @Override
    public SeriesCreateResponseDTO createSeries(Long userId, SeriesCreateRequestDTO dto) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getMessage()));
        Series series = seriesJpaRepository.save(new Series(user, dto));
        return new SeriesCreateResponseDTO(series);
    }
  
    @Override
    public RecentViewCreateResponseDTO recentViewAdd(Long userId, Long postId) {
      User user = userJpaRepository.findById(userId)
              .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_USER_DATA_ERROR.getMessage()));
      Post post = postJpaRepository.findById(postId)
              .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_POST_DATA_ERROR.getMessage()));

      RecentPostId recentPostId = new RecentPostId(userId,postId);

      if(recentPostJpaRepository.existsById(recentPostId)){
          recentPostJpaRepository.deleteById(recentPostId);
          RecentPost recentPost = recentPostJpaRepository.save(new RecentPost(user,post));
          return new RecentViewCreateResponseDTO(recentPost);
      }else {
          RecentPost recentPost = recentPostJpaRepository.save(new RecentPost(user, post));
          return new RecentViewCreateResponseDTO(recentPost);
      }
    }
}
