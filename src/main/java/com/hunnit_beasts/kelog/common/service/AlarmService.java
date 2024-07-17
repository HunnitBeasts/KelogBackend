package com.hunnit_beasts.kelog.common.service;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.common.enumeration.AlarmType;
import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostLikeResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowIngResponseDTO;

public interface AlarmService {
    void newLikeAlarm(Long senderId, PostLikeResponseDTO dto, AlarmType alarmType);
    void newFollowAlarm(Long senderId, FollowIngResponseDTO dto, AlarmType alarmType);
    void newCommentAlarm(Long senderId, CommentCreateResponseDTO dto, AlarmType alarmType);
    void newPostAlarm(Long senderId, FollowIngResponseDTO dto, AlarmType alarmType);
}
