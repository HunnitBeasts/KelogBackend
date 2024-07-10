package com.hunnit_beasts.kelog.postassist.dto.response;

import com.hunnit_beasts.kelog.postassist.entity.domain.Series;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeriesCreateResponseDTO {
    private Long id;
    private String seriesName;
    private String url;
    private Long userId;

    public  SeriesCreateResponseDTO(Series series){
        this.id = series.getId();
        this.seriesName = series.getSeriesName();
        this.url = series.getUrl();
        this.userId = series.getUser().getId();
    }
}
