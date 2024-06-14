package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostJpaRepository extends CrudRepository<Post,Long> {
}
