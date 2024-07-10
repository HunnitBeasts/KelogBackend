package com.hunnit_beasts.kelog.post.entity.domain;

import com.hunnit_beasts.kelog.common.entity.superclass.BaseEntity;
import com.hunnit_beasts.kelog.post.dto.request.SeriesCreateRequestDTO;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private final List<SeriesPost> series = new ArrayList<>();

    public Series(User user, SeriesCreateRequestDTO dto){
        this.seriesName = dto.getSeriesName();
        this.url = dto.getUrl();
        this.user = user;
    }
}
