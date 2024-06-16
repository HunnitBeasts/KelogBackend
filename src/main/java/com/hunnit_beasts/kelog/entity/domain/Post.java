package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.dto.request.post.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.entity.superclass.BaseEntity;
import com.hunnit_beasts.kelog.enumeration.types.PostType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private Boolean isPublic;

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
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<PostViewCnt> postViewCnts = new ArrayList<>();

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


    public Post(PostCreateRequestDTO dto, User user){
        this.title = dto.getTitle();
        this.type = dto.getType();
        this.thumbImage = dto.getThumbImage();
        this.isPublic = dto.getIsPublic();
        this.shortContent = dto.getShortContent();
        this.url = dto.getUrl();
        this.postContent = new PostContent(dto.getContent(),this);
        this.user = user;
    }
}
