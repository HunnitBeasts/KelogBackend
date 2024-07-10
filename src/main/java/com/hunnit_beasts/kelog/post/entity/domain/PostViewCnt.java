package com.hunnit_beasts.kelog.post.entity.domain;

import com.hunnit_beasts.kelog.post.entity.compositekey.PostViewCntId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostViewCnt {

    @Id
    private PostViewCntId id;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long viewCnt;

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PostViewCnt(Post post){
        this.id = new PostViewCntId(post.getId());
        this.viewCnt = 1L;
        this.post = post;
    }

    public PostViewCnt plusViewCnt(){
        viewCnt++;
        return this;
    }
}
