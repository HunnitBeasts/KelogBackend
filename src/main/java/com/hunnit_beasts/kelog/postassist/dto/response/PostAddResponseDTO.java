package com.hunnit_beasts.kelog.postassist.dto.response;

import com.hunnit_beasts.kelog.postassist.entity.domain.SeriesPost;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostAddResponseDTO {
    private Long postId;
    private Long seriesId;
    private Long seriesOrder;

    public PostAddResponseDTO(SeriesPost seriesPost){
        this.postId = seriesPost.getPost().getId();
        this.seriesId = seriesPost.getSeries().getId();
        this.seriesOrder = seriesPost.getSeriesOrder();
    }
}