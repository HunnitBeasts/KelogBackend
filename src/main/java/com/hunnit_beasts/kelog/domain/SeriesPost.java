package com.hunnit_beasts.kelog.domain;


import com.hunnit_beasts.kelog.compositekey.SeriesPostId;
import jakarta.persistence.*;

@Entity
public class SeriesPost {

    @EmbeddedId
    private SeriesPostId id;

    @MapsId("seriesId")
    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @MapsId("postId")
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
