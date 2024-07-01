package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.domain.CommentContent;
import org.springframework.data.repository.CrudRepository;

public interface CommentContentJpaRepository extends CrudRepository<CommentContent, Long> {
}
