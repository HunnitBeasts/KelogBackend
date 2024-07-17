package com.hunnit_beasts.kelog.user.controller;

import com.hunnit_beasts.kelog.auth.aop.Identification;
import com.hunnit_beasts.kelog.user.dto.request.SocialUpdateRequestDTO;
import com.hunnit_beasts.kelog.user.dto.response.SocialUpdateResponseDTO;
import com.hunnit_beasts.kelog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/social")
@RequiredArgsConstructor
public class SocialController {

    private final UserService userService;

    @PatchMapping("/{user-id}")
    @Identification
    public ResponseEntity<SocialUpdateResponseDTO> deleteSocial(@PathVariable(value = "user-id") Long userId,
                                                                @RequestBody SocialUpdateRequestDTO dto,
                                                                Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.socialUpdate(userId,dto.getSocials()));
    }
}
