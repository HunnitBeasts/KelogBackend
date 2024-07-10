package com.hunnit_beasts.kelog.post.repository.jpa;

import com.hunnit_beasts.kelog.post.entity.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagJpaRepository extends CrudRepository<Tag,String> {
}
