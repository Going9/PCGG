package com.ssafy.pcgg.domain.user;

import com.ssafy.pcgg.domain.auth.CurrentUserId;
import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.user.dto.UserListResponse;
import com.ssafy.pcgg.domain.user.dto.UserMyResponse;
import com.ssafy.pcgg.domain.user.dto.UserPeripheralResponse;
import com.ssafy.pcgg.domain.user.dto.UserSignupRequest;
import jakarta.servlet.http.HttpServletRequest;
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
        return ResponseEntity.status(201).body("회원가입");
    }

    @GetMapping
    @CurrentUserId("userId")
    public ResponseEntity<UserMyResponse> getMyUser(UserIdDto userId, HttpServletRequest request) {
        UserMyResponse userMyResponse = userService.getMyUser(userId.getUserId());
        return ResponseEntity.ok().body(userMyResponse);
    }

    @GetMapping("/{category}")
    @CurrentUserId("userId")
    public ResponseEntity<List<UserPeripheralResponse>> getMyPeripherals(UserIdDto userId, HttpServletRequest request, @PathVariable String category) {
        List<UserPeripheralResponse> userPeripheralResponseList = userService.getMyPeripherals(userId.getUserId(), category);
        return ResponseEntity.ok().body(userPeripheralResponseList);
    }

//    @GetMapping
//    public ResponseEntity<List<UserListResponse>> getUsers() {
//        List<UserListResponse> userListResponse = userService.getUsers();
//        return ResponseEntity.ok().body(userListResponse);
//    }

}
