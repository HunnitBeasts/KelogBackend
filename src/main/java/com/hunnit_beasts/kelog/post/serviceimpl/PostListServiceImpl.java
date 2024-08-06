package com.hunnit_beasts.kelog.post.serviceimpl;

import com.hunnit_beasts.kelog.post.dto.request.PostPageRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.UserLikePostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;
import com.hunnit_beasts.kelog.post.repository.querydsl.PostListQueryDSLRepository;
import com.hunnit_beasts.kelog.post.service.PostListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostListServiceImpl implements PostListService {

    private final PostListQueryDSLRepository postListQueryDSLRepository;

    @Override
    public PostPageResponseDTO readPostList(PostPageRequestDTO dto) {
        return postListQueryDSLRepository.findByPostPageDTOs(dto);
    }

    @Override
    public PostPageResponseDTO readLikePosts(UserLikePostRequestDTO dto) {
        return postListQueryDSLRepository.findByLikePostDTOs(dto);
    }
}
