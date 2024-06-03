package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.enumeration.types.SocialType;
import jakarta.persistence.*;

@Entity
public class SocialInfo {

    @Id
    private Long id;

    @Column(nullable = false)
    private SocialType type;

    @Column(length = 512,nullable = false)
    private String link;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

}
