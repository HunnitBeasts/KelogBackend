package com.hunnit_beasts.kelog.post.repository.querydsl;

import com.hunnit_beasts.kelog.post.dto.request.PostPageRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.TrendPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.UserRelatedPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;

public interface PostListQueryDSLRepository {
    PostPageResponseDTO findByPostPageDTOs(PostPageRequestDTO dto);
    PostPageResponseDTO findByLikePostDTOs(UserRelatedPostRequestDTO dto);
    PostPageResponseDTO findByTrendPostDTOs(TrendPostRequestDTO dto);
    PostPageResponseDTO findByRecentPostDTOs(UserRelatedPostRequestDTO dto);
}

