package com.hunnit_beasts.kelog.user.repository.jpa;

import com.hunnit_beasts.kelog.user.entity.compositekey.SocialInfoId;
import com.hunnit_beasts.kelog.user.entity.domain.Social;
import org.springframework.data.repository.CrudRepository;

public interface SocialJpaRepository extends CrudRepository<Social, SocialInfoId> {
}
