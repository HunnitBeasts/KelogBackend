package com.hunnit_beasts.kelog.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {

    @Id
    @Column(length = 32, nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TagPost> tags = new ArrayList<>();

}
