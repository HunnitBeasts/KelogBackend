package com.hunnit_beasts.kelog.user.repository.jpa;

import com.hunnit_beasts.kelog.user.entity.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends CrudRepository<User,Long> {

    Optional<User> findByUserId(String userId);
}
