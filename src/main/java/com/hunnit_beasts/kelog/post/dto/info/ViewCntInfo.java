package com.hunnit_beasts.kelog.post.dto.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ViewCntInfo {
    private Long viewCnt;
    private LocalDateTime regDate;
}
