package com.hunnit_beasts.kelog.postassist.repository;

import java.util.List;

public interface TagQueryDSLRepository {
    void deleteTags(String tag, Long postId);
}
