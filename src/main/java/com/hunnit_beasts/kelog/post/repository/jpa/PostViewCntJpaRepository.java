package com.hunnit_beasts.kelog.post.repository.jpa;

import com.hunnit_beasts.kelog.postassist.entity.domain.compositekey.PostViewCntId;
import com.hunnit_beasts.kelog.post.entity.domain.PostViewCnt;
import org.springframework.data.repository.CrudRepository;

public interface PostViewCntJpaRepository extends CrudRepository<PostViewCnt, PostViewCntId> {
}
