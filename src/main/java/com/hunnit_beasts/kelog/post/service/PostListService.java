package com.hunnit_beasts.kelog.post.service;

import com.hunnit_beasts.kelog.post.dto.request.PostPageRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.TrendPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.UserRelatedPostRequestDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostPageResponseDTO;

public interface PostListService {
    PostPageResponseDTO readPostList(PostPageRequestDTO dto);
    PostPageResponseDTO readLikePosts(UserRelatedPostRequestDTO dto);
    PostPageResponseDTO trendPosts(TrendPostRequestDTO dto);
    PostPageResponseDTO readRecentPosts(UserRelatedPostRequestDTO dto);
}
