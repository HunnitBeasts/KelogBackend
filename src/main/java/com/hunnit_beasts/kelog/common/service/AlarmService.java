package com.hunnit_beasts.kelog.common.service;

import com.hunnit_beasts.kelog.comment.dto.request.CommentCreateRequestDTO;
import com.hunnit_beasts.kelog.post.dto.request.PostLikeRequestDTO;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;

public interface AlarmService {
    void newLikeAlarm(Long senderId, PostLikeRequestDTO dto);
    void newFollowAlarm(Long senderId, FollowIngRequestDTO dto);
    void newCommentAlarm(Long senderId, CommentCreateRequestDTO dto);
    void newPostAlarm(Long senderId);
}
