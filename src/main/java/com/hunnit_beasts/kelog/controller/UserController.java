package com.hunnit_beasts.kelog.controller;

import com.hunnit_beasts.kelog.aop.Identification;
import com.hunnit_beasts.kelog.dto.request.user.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.UserCreateResponseDTO;
import com.hunnit_beasts.kelog.jwt.JwtUtil;
import com.hunnit_beasts.kelog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserCreateResponseDTO> signUp(@RequestBody UserCreateRequestDTO dto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.signUp(dto));
    }
    @GetMapping("/{user-id}")
    public void searchUser(@PathVariable(value = "user-id") Long userId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{user-id}")
    @Identification
    public ResponseEntity<Long> deleteUser(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable(value = "user-id") Long userId) {
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
