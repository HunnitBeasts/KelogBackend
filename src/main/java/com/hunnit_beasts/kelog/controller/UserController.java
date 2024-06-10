package com.hunnit_beasts.kelog.controller;

import com.hunnit_beasts.kelog.dto.request.user.UserCreateRequestDTO;
import com.hunnit_beasts.kelog.dto.response.user.UserCreateResponseDTO;
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

    @PostMapping
    public ResponseEntity<UserCreateResponseDTO> signUp(@RequestBody UserCreateRequestDTO dto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.signUp(dto));
    }

    @GetMapping("/{userId}")
    public void searchUser(@PathVariable(value = "userId") Long userId) {}

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable(value = "userId") Long userId) {}

    @PatchMapping("/{userId}")
    public void updateUserInfo(@PathVariable(value = "userId") Long userId) {}

    @DeleteMapping("/{userId}/thumbnail")
    public void deleteThumbnail(@PathVariable(value = "userId") Long userId){}
}
