package com.hunnit_beasts.kelog.user.controller;

import com.hunnit_beasts.kelog.auth.aop.Identification;
import com.hunnit_beasts.kelog.auth.dto.request.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.auth.dto.response.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import com.hunnit_beasts.kelog.auth.service.AuthenticatedService;
import com.hunnit_beasts.kelog.user.dto.response.UserInfoReadResponseDTO;
import com.hunnit_beasts.kelog.user.dto.response.UserMyInfoReadResponseDTO;
import com.hunnit_beasts.kelog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final AuthenticatedService authenticatedService;

    @PostMapping
    public ResponseEntity<UserCreateResponseDTO> signUp(@RequestBody UserCreateRequestDTO dto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.signUp(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserMyInfoReadResponseDTO> readMyInfo(Authentication authentication) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.readMyInfo(authenticatedService.getId(authentication)));
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserInfoReadResponseDTO> searchUser(@PathVariable(value = "user-id") Long userId,
                                                              Authentication authentication) {
        if(authentication != null)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.readUserInfo(userId,
                            authenticatedService.getId(authentication)));

        else
            return ResponseEntity.status(HttpStatus.OK)
                .body(userService.readUserInfo(userId));
    }

    @DeleteMapping("/{user-id}")
    @Identification
    public ResponseEntity<Long> deleteUser(@PathVariable(value = "user-id") Long userId,
                                           Authentication authentication) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(authService.withDraw(userId));
    }

    @PatchMapping("/{user-id}")
    public void updateUserInfo(@PathVariable(value = "user-id") Long userId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{user-id}/thumbnail")
    public void deleteThumbnail(@PathVariable(value = "user-id") Long userId){
        throw new UnsupportedOperationException();
    }

}
