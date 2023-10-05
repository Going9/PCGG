package com.ssafy.pcgg.domain.user;

import com.ssafy.pcgg.domain.auth.AuthorityEntity;
import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.peripheral.entity.*;
import com.ssafy.pcgg.domain.peripheral.repository.*;
import com.ssafy.pcgg.domain.share.entity.Share;
import com.ssafy.pcgg.domain.share.entity.ShareLike;
import com.ssafy.pcgg.domain.share.repository.ShareLikeRepository;
import com.ssafy.pcgg.domain.share.repository.ShareRepository;
import com.ssafy.pcgg.domain.user.dto.*;
import com.ssafy.pcgg.domain.recommend.entity.QuoteEntity;
import com.ssafy.pcgg.domain.recommend.repository.QuoteSavedRepository;
import com.ssafy.pcgg.domain.user.dto.UserMyResponse;
import com.ssafy.pcgg.domain.user.dto.UserPeripheralResponse;
import com.ssafy.pcgg.domain.user.dto.UserSignupRequest;
import com.ssafy.pcgg.global.handler.ErrorHandler.CustomException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.*;

import static com.ssafy.pcgg.global.handler.ErrorHandler.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ShareRepository shareRepository;
    private final ShareLikeRepository shareLikeRepository;

    private final PeripheralSavedRepository peripheralSavedRepository;
    private final KeyboardRepository keyboardRepository;
    private final MonitorRepository monitorRepository;
    private final MouseRepository mouseRepository;
    private final PrinterRepository printerRepository;
    private final EtcRepository etcRepository;

    private final QuoteSavedRepository quoteSavedRepository;

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private static final String AUTH_CODE_PREFIX = "AuthCode";

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    public void sendEmail(String toEmail) {

        // 이메일 중복 검사
        Optional<UserEntity> userEntity = userRepository.findByEmail(toEmail);
        if (userEntity.isPresent()) {
//            UserEntity userEntity1 = userRepository.findByEmail(toEmail)
//                    .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));
            if (userEntity.get().isActivated()) {
                throw new CustomException(EMAIL_DUPLICATE);
            }
        }

        String title = "pcgg 이메일 인증번호";
        String code = createCode();
        String text = "pcgg 회원가입 인증번호입니다. ";
        text += "인증번호는 " + code + " 입니다.";

        SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);

        try {
            javaMailSender.send(emailForm);
        } catch (RuntimeException e) {
            throw new CustomException(EMAIL_SEND_ERROR);
        }

        try {
            redisService.setValues(AUTH_CODE_PREFIX + toEmail, code, Duration.ofMillis(this.authCodeExpirationMillis));
        } catch (RuntimeException e) {
            throw new CustomException(REDIS_ERROR);
        }
    }

    public boolean verifiedCode(EmailRequest emailRequest) {
        String email = emailRequest.getEmail();
        String authCode = emailRequest.getAuthCode();

        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);

        return authResult;
    }

    private String createCode() {
        int length = 6;
        try {
//            Random random = SecureRandom.getInstanceStrong();
            Random random = new SecureRandom();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (RuntimeException e) {
            throw new CustomException(EMAIL_CODE_ERROR);
        }
    }

    public SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }

    public void signup(UserSignupRequest userSignupRequest) {
        String email = userSignupRequest.getEmail();

        // 이메일 인증에서 이미 중복 확인
//        if (userRepository.findOneWithAuthoritiesByEmail(email).orElse(null) != null) {
//            throw new DuplicateUserException("이미 가입되어 있는 유저입니다.");
//        }

        String password = userSignupRequest.getPassword();
        String name = userSignupRequest.getName();
        String nickname = userSignupRequest.getNickname();

//        Optional<UserEntity> ue = userRepository.findByNickname(nickname);
//        if (ue.isPresent()) {
//            throw new CustomException(NICKNAME_DUPLICATE);
//        }

        AuthorityEntity authorityEntity = AuthorityEntity.builder()
                .authorityName("ROLE_USER")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .activated(true)
                .authorities(Collections.singleton(authorityEntity))
                .build();

        Optional<UserEntity> ue = userRepository.findByEmail(email);
        if (ue.isPresent()) {
            ue.get().reSignup(passwordEncoder.encode(password), name, nickname);
        } else {
            userRepository.save(userEntity);
        }
    }

    public UserMyResponse getMyUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException());

        return UserMyResponse.builder()
                .userId(userId)
                .nickname(userEntity.getNickname())
                .build();
    }

    // peripheral 관련 class 상속 구조로 리팩토링 필요!!
    public List<UserPeripheralResponse> getMyPeripherals(Long userId, String category) {
        List<PeripheralSaved> peripheralSaved = peripheralSavedRepository.findByUser_UserIdAndPeripheralTypeNs_Name(userId, category);

        List<UserPeripheralResponse> userPeripheralResponseList= new ArrayList<>();

        for (PeripheralSaved ps : peripheralSaved) {

            UserPeripheralResponse userPeripheralResponse = null;

            if (category.equals("keyboard")) {
                Keyboard userPeripheral = keyboardRepository.findById(ps.getPeripheralId())
                        .orElseThrow(() -> new RuntimeException());
                userPeripheralResponse = UserPeripheralResponse.builder().id(ps.getId()).name(userPeripheral.getName()).lprice(userPeripheral.getLprice()).hprice(userPeripheral.getHprice()).imageSource(userPeripheral.getImageSource()).link(userPeripheral.getLink()).brand(userPeripheral.getBrand()).build();
            } else if (category.equals("monitor")) {
                Monitor userPeripheral = monitorRepository.findById(ps.getPeripheralId())
                        .orElseThrow(() -> new RuntimeException());
                userPeripheralResponse = UserPeripheralResponse.builder().id(ps.getId()).name(userPeripheral.getName()).lprice(userPeripheral.getLprice()).hprice(userPeripheral.getHprice()).imageSource(userPeripheral.getImageSource()).link(userPeripheral.getLink()).brand(userPeripheral.getBrand()).build();
            } else if (category.equals("mouse")) {
                Mouse userPeripheral = mouseRepository.findById(ps.getPeripheralId())
                        .orElseThrow(() -> new RuntimeException());
                userPeripheralResponse = UserPeripheralResponse.builder().id(ps.getId()).name(userPeripheral.getName()).lprice(userPeripheral.getLprice()).hprice(userPeripheral.getHprice()).imageSource(userPeripheral.getImageSource()).link(userPeripheral.getLink()).brand(userPeripheral.getBrand()).build();
            } else if (category.equals("printer")) {
                Printer userPeripheral = printerRepository.findById(ps.getPeripheralId())
                        .orElseThrow(() -> new RuntimeException());
                userPeripheralResponse = UserPeripheralResponse.builder().id(ps.getId()).name(userPeripheral.getName()).lprice(userPeripheral.getLprice()).hprice(userPeripheral.getHprice()).imageSource(userPeripheral.getImageSource()).link(userPeripheral.getLink()).brand(userPeripheral.getBrand()).build();
            } else if (category.equals("etc")) {
                Etc userPeripheral = etcRepository.findById(ps.getPeripheralId())
                        .orElseThrow(() -> new RuntimeException());
                userPeripheralResponse = UserPeripheralResponse.builder().id(ps.getId()).name(userPeripheral.getName()).lprice(userPeripheral.getLprice()).hprice(userPeripheral.getHprice()).imageSource(userPeripheral.getImageSource()).link(userPeripheral.getLink()).brand(userPeripheral.getBrand()).build();
            } else {
                throw new IllegalArgumentException();
            }

            userPeripheralResponseList.add(userPeripheralResponse);

        }

        return userPeripheralResponseList;
    }

    public List<UserShareResponse> getMyShare(Long userId) {
        List<Share> shareList = shareRepository.findByUser_UserId(userId);

        List<UserShareResponse> userShareResponseList = new ArrayList<>();

        for (Share s : shareList) {
            userShareResponseList.add(UserShareResponse.builder()
                            .id(s.getId())
                            .quoteId(s.getQuote().getId())
                            .title(s.getTitle())
                            .content(s.getContent())
                            .summary(s.getSummary())
                            .createdAt(s.getCreatedAt())
                            .build());
        }

        return userShareResponseList;
    }

    public List<UserShareResponse> getMyShareLike(Long userId) {
        List<ShareLike> shareLikeList = shareLikeRepository.findByUser_UserId(userId);

        List<UserShareResponse> userShareResponseList = new ArrayList<>();

        for (ShareLike sl : shareLikeList) {
            if (sl.getMark() != 1) {
                continue;
            }

            Share s = shareRepository.findById(sl.getShare().getId())
                    .orElseThrow(() -> new RuntimeException());

            userShareResponseList.add(UserShareResponse.builder()
                            .id(s.getId())
                            .quoteId(s.getQuote().getId())
                            .title(s.getTitle())
                            .content(s.getContent())
                            .summary(s.getSummary())
                            .createdAt(s.getCreatedAt())
                            .build());
        }

        return userShareResponseList;
    }

    @Transactional
    public Slice<QuoteEntity> getMyQuotes(UserIdDto userId, int pages){
        PageRequest pageRequest = PageRequest.of(pages, 30, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<QuoteEntity> quoteEntities = quoteSavedRepository.findQuotesByUserId(userId.getUserId(), pageRequest);

        return quoteEntities;
    }

    public String withdrawal(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));

        userEntity.withdrawal();

        return "회원탈퇴 완료";
    }
    
//    public List<UserListResponse> getUsers() {
//        return userRepository.findAll().stream()
//                .map(UserListResponse::new)
//                .collect(Collectors.toList());
//    }
}
