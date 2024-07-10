package com.hunnit_beasts.kelog.comment.repository;

import com.hunnit_beasts.kelog.comment.entity.domain.CommentContent;
import org.springframework.data.repository.CrudRepository;

public interface CommentContentJpaRepository extends CrudRepository<CommentContent, Long> {
}
