package com.hunnit_beasts.kelog.post.dto.superclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageRequestDTO {
    protected Long size;
    protected Long page;
    protected String search;
    protected String sort;
}
