package com.hunnit_beasts.kelog.entity.domain;

import com.hunnit_beasts.kelog.entity.compositekey.TagPostId;
import jakarta.persistence.*;

@Entity
public class TagPost {

    @EmbeddedId
    private TagPostId id;

    @MapsId("tagName")
    @ManyToOne
    @JoinColumn(name = "tag_name")
    private Tag tag;

    @MapsId("postId")
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
