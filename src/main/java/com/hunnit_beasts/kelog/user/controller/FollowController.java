package com.hunnit_beasts.kelog.user.controller;


import com.hunnit_beasts.kelog.auth.service.AuthenticatedService;
import com.hunnit_beasts.kelog.common.event.FollowEvent;
import com.hunnit_beasts.kelog.user.dto.request.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowDeleteResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.FollowIngResponseDTO;
import com.hunnit_beasts.kelog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/following")
public class FollowController {

    private final UserService userService;
    private final AuthenticatedService authenticatedService;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping
    public ResponseEntity<FollowIngResponseDTO> addFollow(@RequestBody FollowIngRequestDTO dto,
                                                          Authentication authentication) {
        Long userId = authenticatedService.getId(authentication);

        FollowIngResponseDTO responseDTO = userService.following(userId,dto);

        eventPublisher.publishEvent(new FollowEvent(responseDTO));

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDTO);
    }

    @DeleteMapping("/{followee-id}")
    public ResponseEntity<FollowDeleteResponseDTO> deleteFollow(@PathVariable(value = "followee-id") Long followeeId,
                                                                Authentication authentication) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(userService.unFollow(authenticatedService.getId(authentication),followeeId));
    }

}
