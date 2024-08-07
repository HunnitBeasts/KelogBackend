package com.hunnit_beasts.kelog.post.service;

import com.hunnit_beasts.kelog.post.dto.request.TrendPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;

public interface TrendCachingService {
    void updateTrendCache();
    PostPageResponseDTO getCachedTrendPosts(TrendPostRequestDTO dto);
}
