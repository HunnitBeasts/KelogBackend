package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.compositekey.PostViewCntId;
import com.hunnit_beasts.kelog.entity.domain.PostViewCnt;
import org.springframework.data.repository.CrudRepository;

public interface PostViewCntJpaRepository extends CrudRepository<PostViewCnt, PostViewCntId> {
}
