package com.hunnit_beasts.kelog.post.repository.querydsl;

import com.hunnit_beasts.kelog.post.dto.request.PostPageRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.TrendPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.UserLikePostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;

public interface PostListQueryDSLRepository {
    PostPageResponseDTO findByPostPageDTOs(PostPageRequestDTO dto);
    PostPageResponseDTO findByLikePostDTOs(UserLikePostRequestDTO dto);
    PostPageResponseDTO findByTrendPostDTOs(TrendPostRequestDTO dto);
}

