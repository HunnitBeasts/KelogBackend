package com.hunnit_beasts.kelog.controller;


import com.hunnit_beasts.kelog.dto.request.user.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.FollowDeleteResponseDTO;
import com.hunnit_beasts.kelog.dto.response.user.FollowIngResponseDTO;
import com.hunnit_beasts.kelog.service.AuthenticatedService;
import com.hunnit_beasts.kelog.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<FollowIngResponseDTO> addFollow(@RequestBody FollowIngRequestDTO dto,
                                                          Authentication authentication) {
        Long userId = authenticatedService.getId(authentication);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.following(userId,dto));
    }

    @DeleteMapping("/{followee-id}")
    public ResponseEntity<FollowDeleteResponseDTO> deleteFollow(@PathVariable(value = "followee-id") Long followeeId,
                                                                Authentication authentication) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(userService.unFollow(authenticatedService.getId(authentication),followeeId));
    }

}
