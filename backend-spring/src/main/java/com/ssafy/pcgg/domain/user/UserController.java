package com.ssafy.pcgg.domain.user;

import com.ssafy.pcgg.domain.user.dto.UserListResponse;
import com.ssafy.pcgg.domain.user.dto.UserSignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> singup(@Valid @RequestBody UserSignupRequest userSignupRequest) {
        userService.signup(userSignupRequest);
        return ResponseEntity.ok("회원가입");
    }

    @GetMapping
    public ResponseEntity<List<UserListResponse>> getUsers() {
        List<UserListResponse> userListResponse = userService.getUsers();
        return ResponseEntity.ok().body(userListResponse);
    }

}
