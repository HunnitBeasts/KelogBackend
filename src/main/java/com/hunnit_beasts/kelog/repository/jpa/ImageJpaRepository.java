package com.hunnit_beasts.kelog.repository.jpa;

import com.hunnit_beasts.kelog.entity.domain.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageJpaRepository extends CrudRepository<Image, String> {
}
