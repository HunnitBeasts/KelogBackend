package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.entity.superclass.BaseEntity;
import com.hunnit_beasts.kelog.enumeration.types.PostType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 256,nullable = false)
    private String title;

    @Column(nullable = false)
    private PostType type;

    @Column(length = 256)
    private String thumbImage;

    @Column(nullable = false)
    private Boolean disclosure;

    @Column(length = 256, nullable = false)
    private String shortContent;

    @Column(length = 128,nullable = false)
    private String url;

    @OneToOne(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private PostContent postContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<PostViewCnt> postViewCnts = new ArrayList<>();

    @OneToOne(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private IncompletePost incompletePost;

    @OneToOne(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private LikedPost likedPost;

    @OneToOne(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private RecentPost recentPost;

    @OneToOne(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private SeriesPost seriesPost;

    @OneToOne(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private TagPost tagPost;

}
