package com.hunnit_beasts.kelog.controllerv2;

import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.user.entity.domain.SocialInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/users")
public class UserController {

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserCreateRequestDTO dto) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo() {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId,
                                        @RequestBody UserCreateRequestDTO userDto) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{userId}/following")
    public ResponseEntity<?> followUser(@PathVariable Long userId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{userId}/following/{followeeId}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long userId, @PathVariable Long followeeId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{userId}/social")
    public ResponseEntity<?> addSocial(@PathVariable Long userId, @RequestBody SocialInfo socialDto) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{userId}/social/{socialId}")
    public ResponseEntity<?> removeSocial(@PathVariable Long userId, @PathVariable Long socialId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{userId}/notifications")
    public ResponseEntity<?> getNotifications(@PathVariable Long userId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{userId}/tags")
    public ResponseEntity<?> getUserTags(@PathVariable Long userId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{userId}/series")
    public ResponseEntity<?> getUserSeries(@PathVariable Long userId) {
        throw new UnsupportedOperationException();
    }
}