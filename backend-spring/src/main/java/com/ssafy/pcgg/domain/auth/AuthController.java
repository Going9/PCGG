package com.ssafy.pcgg.domain.auth;

import com.ssafy.pcgg.domain.auth.dto.AuthLoginRequest;
import com.ssafy.pcgg.domain.auth.dto.AuthLoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthLoginResponse> login(@Valid @RequestBody AuthLoginRequest authLoginRequest) {
        AuthLoginResponse authLoginResponse = authService.login(authLoginRequest);
        return ResponseEntity.ok().body(authLoginResponse);
    }
}