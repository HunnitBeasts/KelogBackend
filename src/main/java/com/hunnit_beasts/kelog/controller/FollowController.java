package com.hunnit_beasts.kelog.controller;


import com.hunnit_beasts.kelog.dto.request.user.FollowIngRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.FollowIngResponseDTO;
import com.hunnit_beasts.kelog.service.ProofService;
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
    private final ProofService proofService;

    @PostMapping
    public ResponseEntity<FollowIngResponseDTO> addFollow(@RequestBody FollowIngRequestDTO dto,
                                                          Authentication authentication) {
        Long userId = proofService.getId(authentication);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.following(userId,dto));
    }

    @DeleteMapping("/{follower-id}/{followee-id}")
    public void deleteFollow(@PathVariable(value = "follower-id") Long followerId,
                             @PathVariable(value = "followee-id") Long followeeId) {
        throw new UnsupportedOperationException();
    }

}
