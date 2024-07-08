package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagJpaRepository extends CrudRepository<Tag,String> {
}
