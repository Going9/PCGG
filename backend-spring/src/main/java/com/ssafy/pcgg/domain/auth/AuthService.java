package com.ssafy.pcgg.domain.auth;

import com.ssafy.pcgg.domain.auth.dto.AuthLoginRequest;
import com.ssafy.pcgg.domain.auth.dto.AuthLoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public AuthLoginResponse login(HttpServletResponse response, AuthLoginRequest authLoginRequest) {
        String email = authLoginRequest.getEmail();
        String password = authLoginRequest.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        // authenticate()가 실행되면, CustomUserDetailsService의 loadUserByEmail이 실행된다.
        // email에 맞지 않는 password를 입력할 시 에러를 던진다. // 이유는 잘 모르겠다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);

        Cookie cookie = new Cookie("accessToken", accessToken);
        response.addCookie(cookie);

        return new AuthLoginResponse(accessToken);
    }
}
