package com.hunnit_beasts.kelog.user.entity.domain;


import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.etc.CustomUserInfoDTO;
import com.hunnit_beasts.kelog.comment.entity.domain.Comment;
import com.hunnit_beasts.kelog.common.entity.domain.Alarm;
import com.hunnit_beasts.kelog.common.entity.superclass.BaseEntity;
import com.hunnit_beasts.kelog.post.entity.domain.IncompletePost;
import com.hunnit_beasts.kelog.post.entity.domain.LikedPost;
import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.post.entity.domain.RecentPost;
import com.hunnit_beasts.kelog.postassist.entity.domain.Series;
import com.hunnit_beasts.kelog.user.enumeration.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Column(nullable = false)
    private UserType userType;

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
    private final List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<Series> seriesList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<Social> socials = new ArrayList<>();

    //복합키를 사용하는 클래스들과의 매핑(alarm, follwer 등등)

    //Alarm
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<Alarm> alarmUsers = new ArrayList<>();

    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<Alarm> targets = new ArrayList<>();

    //Follower
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<Follower> followers = new ArrayList<>();

    @OneToMany(mappedBy = "followee", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<Follower> followees = new ArrayList<>();

    //IncompletePost
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<IncompletePost> incompletePostUsers = new ArrayList<>();

    //LikedPost
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<LikedPost> LikedPostUsers = new ArrayList<>();

    //RecentPost
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<RecentPost> recentPostUsers = new ArrayList<>();

    public User(UserCreateRequestDTO dto) {
        this.userId = dto.getUserId();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.thumbImage = "";
        this.briefIntro = dto.getBriefIntro();
        this.email = dto.getEmail();
        this.emailSetting = Boolean.FALSE;
        this.alarmSetting = Boolean.FALSE;
        this.kelogName = dto.getUserId();
        this.userType = UserType.USER;
        this.userIntro = new UserIntro(this);
    }

    public CustomUserInfoDTO entityToCustomUserInfoDTO(){
        return CustomUserInfoDTO.builder()
                .id(id)
                .userId(userId)
                .password(password)
                .userType(userType)
                .build();
    }

}
