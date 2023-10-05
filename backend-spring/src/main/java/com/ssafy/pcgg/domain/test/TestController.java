package com.ssafy.pcgg.domain.test;

import com.ssafy.pcgg.domain.test.dto.RedisTestDto;
import com.ssafy.pcgg.domain.user.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final RedisService redisService;

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @PostMapping("/redis")
    public ResponseEntity<String> testRedis(@RequestBody RedisTestDto redisTestDto) {
        redisService.setValues(redisTestDto.getKey(), redisTestDto.getValue());
        return ResponseEntity.status(201).body("레디스 성공");
    }
}
