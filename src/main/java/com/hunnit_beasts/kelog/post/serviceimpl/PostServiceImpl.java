package com.hunnit_beasts.kelog.post.serviceimpl;

import com.hunnit_beasts.kelog.common.enumeration.ErrorCode;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import com.hunnit_beasts.kelog.post.dto.info.PostOrderInfo;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostPageRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostUpdateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.*;
import com.hunnit_beasts.kelog.post.entity.compositekey.LikedPostId;
import com.hunnit_beasts.kelog.post.entity.compositekey.PostViewCntId;
import com.hunnit_beasts.kelog.post.entity.compositekey.RecentPostId;
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
import com.hunnit_beasts.kelog.postassist.service.TagService;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import com.hunnit_beasts.kelog.user.repository.jpa.UserJpaRepository;
import com.hunnit_beasts.kelog.user.repository.querydsl.UserQueryDSLRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final LikedPostJpaRepository likedPostJpaRepository;
    private final PostViewCntJpaRepository postViewCntJpaRepository;

    private final PostQueryDSLRepository postQueryDSLRepository;
    private final RecentPostJpaRepository recentPostJpaRepository;
    private final UserQueryDSLRepository userQueryDSLRepository;

    private final TagService tagService;

    @Override
    public PostCreateResponseDTO postCreate(Long userId, PostCreateRequestDTO dto) {
        User creator = userJpaRepository.findById(userId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_USER_DATA_ERROR));

        Post createdPost = postJpaRepository.save(new Post(dto, creator));

        if(dto.getTags() != null)
            tagService.createTagPost(dto.getTags(), createdPost);

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

        Post updatedPost = post.changePost(dto);

        Set<String> existingTags = tagService.getExistingTags(post.getId());
        Set<String> newTags = new HashSet<>(dto.getTags());

        tagService.removeDeletedTags(updatedPost.getId(), existingTags, newTags);
        tagService.addNewTags(updatedPost, existingTags, newTags);

        postJpaRepository.save(updatedPost);

        return postQueryDSLRepository.findPostUpdateResponseDTOById(updatedPost.getId());
    }

    @Override
    public PostViewCountResponseDTO viewCntInfos(Long postId) {
        return postQueryDSLRepository.findViewCntInfosById(postId);
    }

    @Override
    public PostReadResponseDTO postRead(Long postId, Long userId) {
        Post post = postJpaRepository.findById(postId)
                .orElseThrow(() -> new ExpectException(ErrorCode.NO_POST_DATA_ERROR));

        User user = post.getUser();


        if (!post.getIsPublic() && !user.getId().equals(userId))
            throw new ExpectException(ErrorCode.NOT_OPENED_POST_ERROR);


        boolean isUserLoggedIn = userId != null;

        return PostReadResponseDTO.builder()
                .kelogName(user.getKelogName())
                .title(post.getTitle())
                .nickname(user.getNickname())
                .isFollow(isUserLoggedIn && isFollowing(userId, user.getId()))
                .isLike(isUserLoggedIn && isLiked(userId, postId))
                .likeCount(getLikeCount(postId))
                .regDate(post.getRegDate())
                .tags(getTags(postId))
                .content(getPostContent(postId))
                .thumbImage(user.getThumbImage())
                .afterPostInfo(getAfterPostInfo(postId,user.getId()))
                .beforePostInfo(getBeforePostInfo(postId,user.getId()))
                .build();
    }

    @Override
    public Long getPostId(String userId, String url) {
        Long postId = postQueryDSLRepository.getPostIdByUserIdAndPostUrl(userId, url);
        if (postId == null)
            throw new ExpectException(ErrorCode.NO_POST_DATA_ERROR);
        return postId;
    }

    @Override
    public PostPageResponseDTO readPostList(PostPageRequestDTO dto) {
        return postQueryDSLRepository.findByPostPageDTO(dto);
    }

    private boolean isFollowing(Long followerId, Long followeeId) {
        if (followerId == null || followerId.equals(followeeId))
            return false;

        return userQueryDSLRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId);
    }

    private boolean isLiked(Long userId, Long postId) {
        if (userId == null)
            return false;

        return postQueryDSLRepository.existsByUserIdAndPostId(userId, postId);
    }

    private Long getLikeCount(Long postId) {
        return postQueryDSLRepository.countByPostId(postId);
    }

    private List<String> getTags(Long postId) {
        return postQueryDSLRepository.findTagsByPostId(postId);
    }

    private String getPostContent(Long postId) {
        return postQueryDSLRepository.findContentByPostId(postId);
    }

    private PostOrderInfo getAfterPostInfo(Long postId, Long userId) {
        return postQueryDSLRepository.findNextPostByUser(userId,postId);
    }

    private PostOrderInfo getBeforePostInfo(Long postId, Long userId) {
        return postQueryDSLRepository.findPreviousPostByUser(userId,postId);
    }
}
