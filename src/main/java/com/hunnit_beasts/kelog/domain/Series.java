package com.hunnit_beasts.kelog.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64,nullable = false)
    private String seriesName;

    @Column(nullable = false)
    private LocalDateTime regDate;

    @Column(nullable = false)
    private LocalDateTime modDate;

    @ManyToOne
    private User user;

    //SeriesPost
    @OneToMany(mappedBy = "series",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<SeriesPost> series = new ArrayList<>();
}
