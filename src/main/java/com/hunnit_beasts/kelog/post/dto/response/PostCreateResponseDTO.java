package com.hunnit_beasts.kelog.post.dto.response;

import com.hunnit_beasts.kelog.post.dto.convert.PostInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class PostCreateResponseDTO extends PostInfo {
    private final List<String> tags;

    public PostCreateResponseDTO(PostInfo postInfo, List<String> tags){
        super(postInfo.getId(),
                postInfo.getUserId(),
                postInfo.getTitle(),
                postInfo.getType(),
                postInfo.getThumbImage(),
                postInfo.getIsPublic(),
                postInfo.getShortContent(),
                postInfo.getUrl(),
                postInfo.getContent(),
                postInfo.getRegDate(),
                postInfo.getModDate());
        this.tags = tags;
    }
}
