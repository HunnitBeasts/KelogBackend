package com.hunnit_beasts.kelog.post.dto.request;

import com.hunnit_beasts.kelog.post.dto.superclass.PageRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.TrendType;
import lombok.Getter;

@Getter
public class TrendPostRequestDTO extends PageRequestDTO {
    private final TrendType type;

    public TrendPostRequestDTO(Long size, Long page, String search, String sort, TrendType type) {
        super(size, page, search, sort);
        this.type = type;
    }
}
