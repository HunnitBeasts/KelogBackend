package com.hunnit_beasts.kelog.user.entity.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserIntro {

    @Id
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String intro;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public UserIntro(User user) {
        this.user = user;
        this.intro = "";
    }
}
