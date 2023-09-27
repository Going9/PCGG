package com.ssafy.pcgg.domain.user;

import com.ssafy.pcgg.domain.auth.AuthorityEntity;
import com.ssafy.pcgg.domain.peripheral.entity.*;
import com.ssafy.pcgg.domain.peripheral.repository.*;
import com.ssafy.pcgg.domain.user.dto.UserListResponse;
import com.ssafy.pcgg.domain.user.dto.UserMyResponse;
import com.ssafy.pcgg.domain.user.dto.UserPeripheralResponse;
import com.ssafy.pcgg.domain.user.dto.UserSignupRequest;
import com.ssafy.pcgg.domain.user.exception.DuplicateUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PeripheralSavedRepository peripheralSavedRepository;
    private final KeyboardRepository keyboardRepository;
    private final MonitorRepository monitorRepository;
    private final MouseRepository mouseRepository;
    private final PrinterRepository printerRepository;
    private final EtcRepository etcRepository;

    public void signup(UserSignupRequest userSignupRequest) {
        String email = userSignupRequest.getEmail();

        if (userRepository.findOneWithAuthoritiesByEmail(email).orElse(null) != null) {
            throw new DuplicateUserException("이미 가입되어 있는 유저입니다.");
        }

        String password = userSignupRequest.getPassword();
        String name = userSignupRequest.getName();
        String nickname = userSignupRequest.getNickname();

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

        userRepository.save(userEntity);
    }

    public UserMyResponse getMyUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException());

        return UserMyResponse.builder()
                .nickname(userEntity.getNickname())
                .build();
    }

    // peripheral 관련 class 상속 구조로 리팩토링 필요!!
    public List<UserPeripheralResponse> getMyPeripherals(Long userId, String category) {
        List<PeripheralSaved> peripheralSaved = peripheralSavedRepository.findByUser_UserId(userId);

        for (PeripheralSaved ps : peripheralSaved) {
            System.out.print(ps.getId());
            System.out.print(ps.getPeripheralId());
            System.out.print(ps.getUser().getUserId());
            System.out.println(ps.getPeripheralTypeNs());
        }

        List<UserPeripheralResponse> userPeripheralResponseList= new ArrayList<>();

        for (PeripheralSaved ps : peripheralSaved) {

            UserPeripheralResponse userPeripheralResponse = null;

            if (category.equals("keyboard")) {
                Keyboard userPeripheral = keyboardRepository.findById(ps.getPeripheralId())
                        .orElseThrow(() -> new RuntimeException());
                userPeripheralResponse = UserPeripheralResponse.builder().name(userPeripheral.getName()).lprice(userPeripheral.getLprice()).hprice(userPeripheral.getHprice()).imageSource(userPeripheral.getImageSource()).link(userPeripheral.getLink()).brand(userPeripheral.getBrand()).build();
            } else if (category.equals("monitor")) {
                Monitor userPeripheral = monitorRepository.findById(ps.getPeripheralId())
                        .orElseThrow(() -> new RuntimeException());
                userPeripheralResponse = UserPeripheralResponse.builder().name(userPeripheral.getName()).lprice(userPeripheral.getLprice()).hprice(userPeripheral.getHprice()).imageSource(userPeripheral.getImageSource()).link(userPeripheral.getLink()).brand(userPeripheral.getBrand()).build();
            } else if (category.equals("mouse")) {
                Mouse userPeripheral = mouseRepository.findById(ps.getPeripheralId())
                        .orElseThrow(() -> new RuntimeException());
                userPeripheralResponse = UserPeripheralResponse.builder().name(userPeripheral.getName()).lprice(userPeripheral.getLprice()).hprice(userPeripheral.getHprice()).imageSource(userPeripheral.getImageSource()).link(userPeripheral.getLink()).brand(userPeripheral.getBrand()).build();
            } else if (category.equals("printer")) {
                Printer userPeripheral = printerRepository.findById(ps.getPeripheralId())
                        .orElseThrow(() -> new RuntimeException());
                userPeripheralResponse = UserPeripheralResponse.builder().name(userPeripheral.getName()).lprice(userPeripheral.getLprice()).hprice(userPeripheral.getHprice()).imageSource(userPeripheral.getImageSource()).link(userPeripheral.getLink()).brand(userPeripheral.getBrand()).build();
            } else if (category.equals("etc")) {
                Etc userPeripheral = etcRepository.findById(ps.getPeripheralId())
                        .orElseThrow(() -> new RuntimeException());
                userPeripheralResponse = UserPeripheralResponse.builder().name(userPeripheral.getName()).lprice(userPeripheral.getLprice()).hprice(userPeripheral.getHprice()).imageSource(userPeripheral.getImageSource()).link(userPeripheral.getLink()).brand(userPeripheral.getBrand()).build();
            } else {
                throw new IllegalArgumentException();
            }

            userPeripheralResponseList.add(userPeripheralResponse);

        }

        return userPeripheralResponseList;
    }
//    public List<UserListResponse> getUsers() {
//        return userRepository.findAll().stream()
//                .map(UserListResponse::new)
//                .collect(Collectors.toList());
//    }
}
