package com.hunnit_beasts.kelog.postassist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TagQueryDSLRepositoryImpl implements TagQueryDSLRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteTags(String tag, Long postId) {

    }
}
