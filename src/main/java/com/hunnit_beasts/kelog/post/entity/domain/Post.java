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
@Table(name = "post", indexes = {
        @Index(name = "idx_reg_date", columnList = "reg_date")
})
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

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<LikedPost> likedPosts = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<RecentPost> recentPosts = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<SeriesPost> seriesPosts = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<TagPost> tagPosts = new ArrayList<>();


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

    public Post changePost(PostUpdateRequestDTO dto) {
        this.title = dto.getTitle() != null ? dto.getTitle() : title;
        this.type = dto.getType() != null ? dto.getType() : type;
        this.thumbImage = dto.getThumbImage() != null ? dto.getThumbImage() : thumbImage;
        this.isPublic = dto.getIsPublic() != null ? dto.getIsPublic() : isPublic;
        this.shortContent = dto.getShortContent() != null ? dto.getShortContent() : shortContent;
        this.url = dto.getUrl() != null ? dto.getUrl() : url;

        if (dto.getContent() != null) {
            if (this.postContent == null) {
                this.postContent = PostContent.builder()
                        .content(dto.getContent())
                        .post(this)
                        .build();
            } else
                this.postContent.updateContent(dto.getContent());
        }

        return this;
    }
}
