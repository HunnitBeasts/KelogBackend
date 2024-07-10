package com.hunnit_beasts.kelog.image.repository;

import com.hunnit_beasts.kelog.image.domain.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageJpaRepository extends CrudRepository<Image, String> {
}
