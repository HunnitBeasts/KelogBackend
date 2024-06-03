package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.entity.superclass.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Series extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64,nullable = false)
    private String seriesName;

    @Column(length = 128,nullable = false)
    private String url;

    @ManyToOne
    private User user;

    //SeriesPost
    @OneToMany(mappedBy = "series",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<SeriesPost> series = new ArrayList<>();
}
