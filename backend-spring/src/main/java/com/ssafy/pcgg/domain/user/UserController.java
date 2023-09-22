package com.ssafy.pcgg.domain.user;

import com.ssafy.pcgg.domain.auth.CurrentUserId;
import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.user.dto.UserListResponse;
import com.ssafy.pcgg.domain.user.dto.UserSignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> singup(@Valid @RequestBody UserSignupRequest userSignupRequest) {
        userService.signup(userSignupRequest);
        return ResponseEntity.status(201).body("회원가입");
    }

    @GetMapping
    @CurrentUserId("userIdDto")
    public ResponseEntity<List<UserListResponse>> getUsers(UserIdDto userIdDto, HttpServletRequest request) {
        List<UserListResponse> userListResponse = userService.getUsers();
        return ResponseEntity.ok().body(userListResponse);
    }

}
