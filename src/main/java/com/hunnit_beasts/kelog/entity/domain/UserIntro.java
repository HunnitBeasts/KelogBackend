package com.hunnit_beasts.kelog.entity.domain;

import jakarta.persistence.*;

@Entity
public class UserIntro {
    @Id
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String intro;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;


}
