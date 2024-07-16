package com.hunnit_beasts.kelog.post.serviceimpl;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostUpdateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.*;
import com.hunnit_beasts.kelog.post.entity.domain.LikedPost;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.entity.domain.PostViewCnt;
import com.hunnit_beasts.kelog.post.entity.domain.RecentPost;
import com.hunnit_beasts.kelog.post.repository.jpa.LikedPostJpaRepository;
import com.hunnit_beasts.kelog.post.repository.jpa.PostJpaRepository;
import com.hunnit_beasts.kelog.post.repository.jpa.PostViewCntJpaRepository;
import com.hunnit_beasts.kelog.post.repository.jpa.RecentPostJpaRepository;
import com.hunnit_beasts.kelog.post.repository.querydsl.PostQueryDSLRepository;
import com.hunnit_beasts.kelog.post.service.PostService;
import com.hunnit_beasts.kelog.postassist.entity.domain.compositekey.LikedPostId;
import com.hunnit_beasts.kelog.postassist.entity.domain.compositekey.PostViewCntId;
import com.hunnit_beasts.kelog.postassist.entity.domain.compositekey.RecentPostId;
import com.hunnit_beasts.kelog.postassist.service.TagService;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final LikedPostJpaRepository likedPostJpaRepository;
    private final PostViewCntJpaRepository postViewCntJpaRepository;

    private final PostQueryDSLRepository postQueryDSLRepository;
    private final RecentPostJpaRepository recentPostJpaRepository;

    private final TagService tagService;

    @Override
    public PostCreateResponseDTO postCreate(Long userId, PostCreateRequestDTO dto) {
        User creator = userJpaRepository.findById(userId)
                .orElseThrow(()-> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        Post createPostEntity = new Post(dto,creator);
        Post createdPost = postJpaRepository.save(createPostEntity);
        if(dto.getTags() != null)
            tagInsert(dto.getTags(), createdPost);
        return postQueryDSLRepository.findPostCreateResponseDTOById(createdPost.getId());
    }

    private void tagInsert(List<String> tags, Post post){
        for (String tag : tags) tagService.createTag(tag);
        tagService.addTagPost(tags, post);
    }

    @Override
    public Long postDelete(Long postId) {
        postJpaRepository.deleteById(postId);
        return postId;
    }

    @Override
    public PostLikeResponseDTO addPostLike(Long userId, PostLikeRequestDTO dto) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        Post post = postJpaRepository.findById(dto.getPostId())
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));

        if(likedPostJpaRepository.existsById(new LikedPostId(user,post)))
            throw new ExpectException(ErrorCode.POST_LIKE_DUPLICATION_ERROR);
        else{
            LikedPost likedPost = likedPostJpaRepository.save(new LikedPost(user,post));
            return new PostLikeResponseDTO(likedPost);
        }
    }

    @Override
    public PostViewCntResponseDTO plusViewCnt(Long postId) {
        Post plusViewCntPost = postJpaRepository.findById(postId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        PostViewCntId thisPostsViewCnt = new PostViewCntId(plusViewCntPost.getId());
        if(postViewCntJpaRepository.existsById(thisPostsViewCnt)){
            PostViewCnt postViewCnt = postViewCntJpaRepository.findById(thisPostsViewCnt)
                    .orElseThrow(() -> new ExpectException(ErrorCode.NO_POST_VIEW_DATA_ERROR));
            postViewCntJpaRepository.save(postViewCnt.plusViewCnt());
        }else
            postViewCntJpaRepository.save(new PostViewCnt(plusViewCntPost));
        return new PostViewCntResponseDTO(postQueryDSLRepository.findTotalViewCntById(postId));
    }

    @Override
    public PostLikeResponseDTO deletePostLike(Long userId, Long postId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
        Post post = postJpaRepository.findById(postId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        LikedPostId likedPostId = new LikedPostId(user, post);
        if (likedPostJpaRepository.existsById(likedPostId))
            likedPostJpaRepository.deleteById(likedPostId);
        else
            throw new ExpectException(ErrorCode.POST_LIKE_DUPLICATION_ERROR);
        return new PostLikeResponseDTO(userId,postId);
    }

    @Override
    public RecentViewCreateResponseDTO recentViewAdd(Long userId, Long postId) {
      User user = userJpaRepository.findById(userId)
              .orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));
      Post post = postJpaRepository.findById(postId)
              .orElseThrow(() -> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));

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

    @Override
    public PostUpdateResponseDTO postUpdate(Long postId, PostUpdateRequestDTO dto) {
        Post post = postJpaRepository.findById(postId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));
        Post updatedPost = postJpaRepository.save(post.changePost(dto));
        return postQueryDSLRepository.findPostUpdateResponseDTOById(updatedPost.getId());
    }

    @Override
    public PostViewCountResponseDTO viewCntInfos(Long postId) {
        return postQueryDSLRepository.findViewCntInfosById(postId);
    }
}