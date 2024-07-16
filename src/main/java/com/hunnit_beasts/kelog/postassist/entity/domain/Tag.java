package com.hunnit_beasts.kelog.postassist.entity.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Tag {

    @Id
    @Column(length = 32, nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<TagPost> tags = new ArrayList<>();

}
