package com.hunnit_beasts.kelog.comment.repository;

import com.hunnit_beasts.kelog.comment.entity.domain.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentJpaRepository extends CrudRepository<Comment,Long> {
}
