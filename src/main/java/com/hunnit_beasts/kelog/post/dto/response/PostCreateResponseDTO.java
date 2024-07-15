package com.hunnit_beasts.kelog.post.dto.response;

import com.hunnit_beasts.kelog.post.dto.convert.PostInfos;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostCreateResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private PostType type;
    private String thumbImage;
    private Boolean isPublic;
    private String shortContent;
    private String url;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private List<String> tags;

    public PostCreateResponseDTO(PostInfos postInfos, List<String> tags){
        this.id = postInfos.getId();
        this.userId = postInfos.getUserId();
        this.title = postInfos.getTitle();
        this.type = postInfos.getType();
        this.thumbImage = postInfos.getThumbImage();
        this.isPublic = postInfos.getIsPublic();
        this.shortContent = postInfos.getShortContent();
        this.url = postInfos.getUrl();
        this.content = postInfos.getContent();
        this.regDate = postInfos.getRegDate();
        this.modDate = postInfos.getModDate();
        this.tags = tags;
    }
}
