package com.hunnit_beasts.kelog.post.repository.jpa;

import com.hunnit_beasts.kelog.post.entity.domain.PostViewCnt;
import com.hunnit_beasts.kelog.post.entity.compositekey.PostViewCntId;
import org.springframework.data.repository.CrudRepository;

public interface PostViewCntJpaRepository extends CrudRepository<PostViewCnt, PostViewCntId> {
}
