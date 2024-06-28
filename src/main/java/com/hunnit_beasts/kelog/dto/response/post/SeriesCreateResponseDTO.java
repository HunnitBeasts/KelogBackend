package com.hunnit_beasts.kelog.dto.response.post;

import com.hunnit_beasts.kelog.entity.domain.Series;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
