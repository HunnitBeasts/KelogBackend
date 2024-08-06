package com.hunnit_beasts.kelog.common.eventlistener;

import com.hunnit_beasts.kelog.comment.dto.response.CommentCreateResponseDTO;
import com.hunnit_beasts.kelog.common.event.CommentEvent;
import com.hunnit_beasts.kelog.common.event.FollowEvent;
import com.hunnit_beasts.kelog.common.event.PostEvent;
import com.hunnit_beasts.kelog.common.event.PostLikeEvent;
import com.hunnit_beasts.kelog.common.service.AlarmService;
import com.hunnit_beasts.kelog.post.dto.response.PostCreateResponseDTO;
import com.hunnit_beasts.kelog.post.dto.response.PostLikeResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowIngResponseDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AlarmEventListener {

    private final AlarmService alarmService;

    @EventListener
    public void handlePostEvent(PostEvent event) {
        alarmService.newPostAlarm(event.dto());
    }

    @EventListener
    public void handlePostLikeEvent(PostLikeEvent event) {
        alarmService.newLikeAlarm(event.dto());
    }

    @EventListener
    public void handleFollowEvent(FollowEvent event) {
        alarmService.newFollowAlarm(event.dto());
    }

    @EventListener
    public void handleCommentEvent(CommentEvent event) {
        alarmService.newCommentAlarm(event.dto());
    }
}
