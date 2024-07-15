package com.hunnit_beasts.kelog.post.dto.response;

import com.hunnit_beasts.kelog.post.dto.info.ViewCntInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@AllArgsConstructor
public class PostViewCountResponseDTO {
    private Long allDayView;
    private Long todayView;
    private Long yesterdayView;
    private List<ViewCntInfo> views;
    private LocalDateTime regDate;
}
