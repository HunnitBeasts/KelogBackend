package com.hunnit_beasts.kelog.postassist.repository;

import com.hunnit_beasts.kelog.postassist.entity.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagJpaRepository extends CrudRepository<Tag,String> {
}
