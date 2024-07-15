package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.entity.compositekey.TagPostId;
import com.hunnit_beasts.kelog.postassist.entity.domain.TagPost;
import org.springframework.data.repository.CrudRepository;

public interface TagPostJpaRepository extends CrudRepository<TagPost, TagPostId> {
}
