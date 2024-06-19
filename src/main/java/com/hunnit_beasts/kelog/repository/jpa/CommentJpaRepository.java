package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.domain.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentJpaRepository extends CrudRepository<Comment,Long> {
}
