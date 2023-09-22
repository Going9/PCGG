package com.ssafy.pcgg.domain.auth;

import com.ssafy.pcgg.domain.auth.dto.AuthLoginRequest;
import com.ssafy.pcgg.domain.auth.dto.AuthLoginResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthLoginResponse> login(HttpServletResponse response, @Valid @RequestBody AuthLoginRequest authLoginRequest) throws UnsupportedEncodingException {
        AuthLoginResponse authLoginResponse = authService.login(response, authLoginRequest);
        return ResponseEntity.ok().body(authLoginResponse);
    }
}