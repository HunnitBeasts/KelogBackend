package com.hunnit_beasts.kelog.domain;

import com.hunnit_beasts.kelog.converter.PostTypeConverter;
import com.hunnit_beasts.kelog.enumeration.PostType;
import com.hunnit_beasts.kelog.superclass.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long views;

    @Column(length = 256,nullable = false)
    private String title;

//    @Convert(converter = PostTypeConverter.class)
    @Column(nullable = false)
    private PostType type;

    @Column(length = 256)
    private String thumbImage;

    @OneToOne(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private PostContent postContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
