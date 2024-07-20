package com.hunnit_beasts.kelog.post.dto.response;

import com.hunnit_beasts.kelog.post.dto.info.PostOrderInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PostReadResponseDTO {
    private String kelogName;
    private String title;
    private String nickname;
    private Boolean isFollow;
    private Boolean isLike;
    private Long likeCount;
    private LocalDateTime regDate;
    private List<String> tags;
    private String content;
    private String thumbImage;
    private PostOrderInfo afterPostInfo;
    private PostOrderInfo beforePostInfo;
}
