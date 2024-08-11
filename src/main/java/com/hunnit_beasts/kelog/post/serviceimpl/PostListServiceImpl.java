package com.hunnit_beasts.kelog.post.serviceimpl;

import com.hunnit_beasts.kelog.post.dto.request.PostPageRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.TrendPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.UserRelatedPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;
import com.hunnit_beasts.kelog.post.repository.querydsl.PostListQueryDSLRepository;
import com.hunnit_beasts.kelog.post.service.PostListService;
import com.hunnit_beasts.kelog.post.service.TrendCachingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostListServiceImpl implements PostListService {

    private final PostListQueryDSLRepository postListQueryDSLRepository;
    private final TrendCachingService trendCachingService;

    @Override
    public PostPageResponseDTO readPostList(PostPageRequestDTO dto) {
        return postListQueryDSLRepository.findByPostPageDTOs(dto);
    }

    @Override
    public PostPageResponseDTO readLikePosts(UserRelatedPostRequestDTO dto) {
        return postListQueryDSLRepository.findByLikePostDTOs(dto);
    }

    @Override
    public PostPageResponseDTO trendPosts(TrendPostRequestDTO dto) {
        return trendCachingService.getCachedTrendPosts(dto);
    }

    @Override
    public PostPageResponseDTO readRecentPosts(UserRelatedPostRequestDTO dto) {
        return postListQueryDSLRepository.findByRecentPostDTOs(dto);
    }
}
