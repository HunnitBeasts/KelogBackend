package com.hunnit_beasts.kelog.postassist.entity.domain;

import com.hunnit_beasts.kelog.post.entity.domain.Post;
import com.hunnit_beasts.kelog.postassist.entity.compositekey.TagPostId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagPost {

    @EmbeddedId
    private TagPostId id;

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public TagPost(String tag, Post post){
        this.id = new TagPostId(tag, post.getId());
        this.post = post;
    }
}