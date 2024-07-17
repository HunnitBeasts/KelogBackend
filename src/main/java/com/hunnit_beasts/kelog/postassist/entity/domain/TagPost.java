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

    @MapsId("tagName")
    @ManyToOne
    @JoinColumn(name = "tag_name")
    private Tag tag;

    @MapsId("postId")
    @OneToOne // m to 1
    @JoinColumn(name = "post_id")
    private Post post;

    public TagPost(Tag tag, Post post){
        this.id = new TagPostId(tag.getTagName(),post.getId());
        this.tag = tag;
        this.post = post;
    }
}
