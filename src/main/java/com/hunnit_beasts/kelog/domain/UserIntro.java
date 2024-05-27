package com.hunnit_beasts.kelog.domain;

import jakarta.persistence.*;

@Entity
public class UserIntro {
    @Id
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String intro;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id") //오류나면 값 user_id 로 바꿔볼것
    User user;


}
