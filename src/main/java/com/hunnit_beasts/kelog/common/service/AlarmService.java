package com.hunnit_beasts.kelog.common.service;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.common.dto.response.AlarmReadResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostLikeResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowIngResponseDTO;

import java.util.List;

public interface AlarmService {
    void newLikeAlarm(PostLikeResponseDTO dto);
    void newFollowAlarm(FollowIngResponseDTO dto);
    void newCommentAlarm(CommentCreateResponseDTO dto);
    void newPostAlarm(PostCreateResponseDTO dto);
    List<AlarmReadResponseDTO> readAlarm(Long userId);
    List<Long> allAlarmCheck(Long userId);
    List<Long> deleteAllAlarm(Long userId);
}
