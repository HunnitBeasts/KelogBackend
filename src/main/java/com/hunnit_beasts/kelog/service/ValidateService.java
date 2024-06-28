package com.hunnit_beasts.kelog.service;

public interface ValidateService {

    void userIdAndUserIdSameCheck(Long id, Long userId);
    void userIdAndPostIdSameCheck(Long id, Long postId);
    void userIdAndCommentIdSameCheck(Long id, Long postId);
}
