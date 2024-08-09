package com.hunnit_beasts.kelog.common.service;

public interface ValidateService {

    void userIdAndUserIdSameCheck(Long id, Long userId);
    void userIdAndPostIdSameCheck(Long id, Long postId);
    void userIdAndCommentIdSameCheck(Long id, Long commentId);
    void userIdAndSeriesIdSameCheck(Long id, Long seriesId);
    void userIdAndAlarmIdSameCheck(Long id, Long alarmId);
}
