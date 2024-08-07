package com.hunnit_beasts.kelog.post.serviceimpl;

import com.hunnit_beasts.kelog.post.dto.convert.PostPageConvert;
import com.hunnit_beasts.kelog.post.dto.request.TrendPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;
import com.hunnit_beasts.kelog.post.enumeration.TrendType;
import com.hunnit_beasts.kelog.post.repository.querydsl.PostListQueryDSLRepository;
import com.hunnit_beasts.kelog.post.service.TrendCachingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TrendCachingServiceImpl implements TrendCachingService {

    private final PostListQueryDSLRepository postListQueryDSLRepository;
    private final CacheManager cacheManager;

    private static final long CACHE_SIZE = 500;

    @Override
    @Scheduled(fixedRate = 6 * 60 * 60 * 1000) // 6시간마다 실행
    public void updateTrendCache() {
        for (TrendType type : TrendType.values()) {
            TrendPostRequestDTO dto = new TrendPostRequestDTO(CACHE_SIZE, 1L, null, "score", type);
            PostPageResponseDTO result = postListQueryDSLRepository.findByTrendPostDTOs(dto);
            Objects.requireNonNull(cacheManager.getCache("trendPosts")).put(type.name(), result);
        }
    }

    @Override
    public PostPageResponseDTO getCachedTrendPosts(TrendPostRequestDTO dto) {
        Cache cache = cacheManager.getCache("trendPosts");
        PostPageResponseDTO cachedResult = cache != null ? cache.get(dto.getType().name(), PostPageResponseDTO.class) : null;

        if (cachedResult == null) {
            TrendPostRequestDTO fullDto = new TrendPostRequestDTO(CACHE_SIZE, 1L, null, "score", dto.getType());
            cachedResult = postListQueryDSLRepository.findByTrendPostDTOs(fullDto);
            Objects.requireNonNull(cache).put(dto.getType().name(), cachedResult);
        }

        int fromIndex = (int) ((dto.getPage() - 1) * dto.getSize());
        int toIndex = Math.min((int) (fromIndex + dto.getSize()), cachedResult.getPosts().size());

        List<PostPageConvert> paginatedPosts = cachedResult.getPosts().subList(fromIndex, toIndex);

        return new PostPageResponseDTO((long) cachedResult.getPosts().size(), paginatedPosts);
    }
}
