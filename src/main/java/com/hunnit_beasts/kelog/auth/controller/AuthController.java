package com.hunnit_beasts.kelog.auth.controller;

import com.hunnit_beasts.kelog.auth.dto.request.UserLoginRequestDTO;
import com.hunnit_beasts.kelog.auth.dto.response.TokenResponseDTO;
import com.hunnit_beasts.kelog.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody UserLoginRequestDTO dto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.login(dto));
    }
}
