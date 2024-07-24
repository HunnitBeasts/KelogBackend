package com.hunnit_beasts.kelog.postassist.entity.domain;


import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.postassist.entity.compositekey.SeriesPostId;
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
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public SeriesPost(Post post, Series series, Long seriesOrder){
        this.id = new SeriesPostId(series.getId(), post.getId());
        this.seriesOrder = seriesOrder + 1;
        this.series = series;
        this.post = post;
    }

    public SeriesPost changeOrder(Long seriesOrder){
        this.seriesOrder = seriesOrder;
        return this;
    }
}
