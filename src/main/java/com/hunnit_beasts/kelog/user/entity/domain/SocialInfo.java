package com.hunnit_beasts.kelog.user.entity.domain;

import com.hunnit_beasts.kelog.user.enumeration.SocialType;
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
