package com.hunnit_beasts.kelog.post.dto.request;

import com.hunnit_beasts.kelog.post.dto.superclass.PageRequestDTO;
import lombok.Getter;

@Getter
public class PostPageRequestDTO extends PageRequestDTO {
    private final String tagName;
    private final Long userId;

    public PostPageRequestDTO(String tagName, String sort, Long page, Long size, String search, Long userId) {
        super(size,page,search,sort);
        this.tagName = tagName;
        this.userId = userId;
    }
}
