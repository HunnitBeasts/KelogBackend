package com.hunnit_beasts.kelog.domain;

import com.hunnit_beasts.kelog.compositekey.TagPostId;
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
