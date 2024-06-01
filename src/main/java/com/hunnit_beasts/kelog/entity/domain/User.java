package com.hunnit_beasts.kelog.entity.domain;


import com.hunnit_beasts.kelog.entity.superclass.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false, unique = true)
    private String userId;

    @Column(length = 128, nullable = false)
    private String password;

    @Column(length = 32,nullable = false,unique = true)
    private String nickname;

    @Column(length = 256)
    private String thumbImage; // default 이름+바탕색

    @Column(length = 256)
    private String briefIntro;

//    @Column
//    private Long theme; 아마 테마 하나로 쓸 것 같긴함

    @Column(length = 128,nullable = false)
    private String email;

    @Column(nullable = false)// true 수신받음, fasle 수신안받음
    private Boolean emailSetting;

    @Column(nullable = false)// true 수신받음, fasle 수신안받음
    private Boolean alarmSetting;

    @Column(length = 64,nullable = false) //default email앞부분.log
    private String kelogName;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private UserIntro userIntro;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Series> seriesList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<SocialInfo> socialInfos = new ArrayList<>();

    //복합키를 사용하는 클래스들과의 매핑(alarm, follwer 등등)

    //Alarm
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Alarm> alarmUsers = new ArrayList<>();

    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Alarm> targets = new ArrayList<>();

    //Follower
    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Follower> followings = new ArrayList<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Follower> followeds = new ArrayList<>();

    //IncompletePost
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<IncompletePost> incompletePostUsers = new ArrayList<>();

    //LikedPost
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<LikedPost> LikedPostUsers = new ArrayList<>();

    //RecentPost
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<RecentPost> recentPostUsers = new ArrayList<>();

}
