package com.hunnit_beasts.kelog.entity.domain;


import com.hunnit_beasts.kelog.entity.compositekey.SeriesPostId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SeriesPost {

    @EmbeddedId
    private SeriesPostId id;

    @Column(nullable = false)
    private Long seriesOrder;

    @MapsId("seriesId")
    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @MapsId("postId")
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public SeriesPost(Post post, Series series, Long seriesOrder){
        this.id = new SeriesPostId(series.getId(), post.getId());
        this.seriesOrder = seriesOrder + 1;
        this.series = series;
        this.post = post;
    }
}
