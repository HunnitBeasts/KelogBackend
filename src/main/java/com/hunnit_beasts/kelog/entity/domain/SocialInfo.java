package com.hunnit_beasts.kelog.entity.domain;

import jakarta.persistence.*;

@Entity
public class SocialInfo {

    @Id
    private Long id;

    @Column(length = 256)
    private String email;

    @Column(length = 256)
    private String github;

    @Column(length = 256)
    private String insta;

    @Column(length = 256)
    private String facebook;

    @Column(length = 256)
    private String homepage;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

}
