package com.hunnit_beasts.kelog.postassist.entity.domain;

import com.hunnit_beasts.kelog.postassist.dto.request.TagCreateRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @Column(length = 32, nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<TagPost> tags = new ArrayList<>();

    public Tag(TagCreateRequestDTO dto){
        this.tagName = dto.getTagName();
    }
}
