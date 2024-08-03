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
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventListener {

    private final AlarmService alarmService;

    @EventListener
    public void handlePostEvent(PostEvent event) {
        PostCreateResponseDTO resDto = event.getDto();
        alarmService.newPostAlarm(resDto);
    }

    @EventListener
    public void handlePostLikeEvent(PostLikeEvent event) {
        PostLikeResponseDTO resDto = event.getDto();
        alarmService.newLikeAlarm(resDto);
    }

    @EventListener
    public void handleFollowEvent(FollowEvent event) {
        FollowIngResponseDTO resDto = event.getDto();
        alarmService.newFollowAlarm(resDto);
    }

    @EventListener
    public void handleCommentEvent(CommentEvent event) {
        CommentCreateResponseDTO resDto = event.getDto();
        alarmService.newCommentAlarm(resDto);
    }
}
