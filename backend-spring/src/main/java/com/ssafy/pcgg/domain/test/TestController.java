package com.ssafy.pcgg.domain.test;

import com.ssafy.pcgg.domain.test.dto.EmailTestDto;
import com.ssafy.pcgg.domain.test.dto.RedisTestDto;
import com.ssafy.pcgg.domain.user.RedisService;
import com.ssafy.pcgg.global.handler.ErrorHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

import static com.ssafy.pcgg.global.handler.ErrorHandler.ErrorCode.EMAIL_ERROR;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final RedisService redisService;
    private final JavaMailSender javaMailSender;

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @PostMapping("/redis")
    public ResponseEntity<String> testRedis(@RequestBody RedisTestDto redisTestDto) {
        redisService.setValues(redisTestDto.getKey(), redisTestDto.getValue());
        return ResponseEntity.status(201).body("레디스 성공");
    }

    @PostMapping("/email")
    public ResponseEntity<String> testEMail(@RequestBody EmailTestDto emailTestDto) {
        String toEmail = emailTestDto.getEmail();

        String title = "TEST";
        String text = "test";
        SimpleMailMessage emilaForm = createEmailForm(toEmail, title, text);

        try {
            javaMailSender.send(emilaForm);
        } catch (RuntimeException e) {
            throw new CustomException(EMAIL_ERROR);
        }

        return ResponseEntity.ok().body("이메일 성공");
    }

    public SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("a01037103261@gmail.com");
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}
