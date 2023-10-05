package com.ssafy.pcgg.domain.test;

import com.ssafy.pcgg.domain.test.dto.EmailTestDto;
import com.ssafy.pcgg.domain.test.dto.RedisTestDto;
import com.ssafy.pcgg.domain.user.RedisService;
import com.ssafy.pcgg.global.handler.ErrorHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

import static com.ssafy.pcgg.global.handler.ErrorHandler.ErrorCode.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final RedisService redisService;
    private final JavaMailSender javaMailSender;

    private static final String AUTH_CODE_PREFIX = "AuthCode";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

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
        String text = createCode();
        SimpleMailMessage emilaForm = createEmailForm(toEmail, title, text);

        try {
            javaMailSender.send(emilaForm);
        } catch (RuntimeException e) {
            throw new CustomException(EMAIL_SEND_ERROR);
        }

        try {
            redisService.setValues(AUTH_CODE_PREFIX + toEmail, text, Duration.ofMillis(this.authCodeExpirationMillis));
        } catch (RuntimeException e) {
            throw new CustomException(REDIS_ERROR);
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

    // test 결과 createCode()가 로컬에서는 연속으로 해도 문제 없지만
    // 서버에서는 트래픽 문제가 발생
    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(EMAIL_CODE_ERROR);
        }
    }
}
