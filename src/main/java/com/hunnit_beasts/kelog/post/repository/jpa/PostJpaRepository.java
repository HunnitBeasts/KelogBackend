package com.hunnit_beasts.kelog.post.repository.jpa;

import com.hunnit_beasts.kelog.post.entity.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostJpaRepository extends CrudRepository<Post,Long> {
}
