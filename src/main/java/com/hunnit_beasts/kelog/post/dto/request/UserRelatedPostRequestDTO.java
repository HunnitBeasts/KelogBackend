package com.hunnit_beasts.kelog.post.dto.request;

import com.hunnit_beasts.kelog.post.dto.superclass.PageRequestDTO;
import lombok.Getter;

@Getter
public class UserRelatedPostRequestDTO extends PageRequestDTO {
    private final Long userId;

    public UserRelatedPostRequestDTO(Long userId, String sort, Long page, Long size, String search) {
        super(size,page,search,sort);
        this.userId = userId;
    }
}
