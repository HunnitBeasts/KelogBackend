package com.hunnit_beasts.kelog.post.entity.domain;

import com.hunnit_beasts.kelog.comment.entity.domain.Comment;
import com.hunnit_beasts.kelog.common.entity.superclass.BaseEntity;
import com.hunnit_beasts.kelog.post.dto.request.PostCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostUpdateRequestDTO;
import com.hunnit_beasts.kelog.post.enumeration.PostType;
import com.hunnit_beasts.kelog.postassist.entity.domain.SeriesPost;
import com.hunnit_beasts.kelog.postassist.entity.domain.TagPost;
import com.hunnit_beasts.kelog.user.entity.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
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

    public Post changePost(PostUpdateRequestDTO dto){
        this.title = dto.getTitle() != null ? dto.getTitle() : title;
        this.type = dto.getType() != null ? dto.getType() : type;
        this.thumbImage = dto.getThumbImage() != null ? dto.getThumbImage() : thumbImage;
        this.isPublic = dto.getIsPublic() != null ? dto.getIsPublic() : isPublic;
        this.shortContent = dto.getShortContent() != null ? dto.getShortContent() : shortContent;
        if (dto.getContent() != null) {
            this.postContent = PostContent.builder()
                    .id(this.postContent.getId())
                    .content(dto.getContent())
                    .post(this)
                    .build();
        }
        this.url = dto.getUrl() != null ? dto.getUrl() : url;
        return this;
    }
}
