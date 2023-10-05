package com.ssafy.pcgg.domain.test;

import com.ssafy.pcgg.domain.user.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final RedisService redisService;

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @GetMapping("/redis")
    public ResponseEntity<String> testRedis(@RequestBody String key, String value) {
        redisService.setValues(key, value);
        return ResponseEntity.status(201).body("레디스 성공");
    }
}
