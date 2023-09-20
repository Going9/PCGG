package com.ssafy.pcgg.domain.auth;

import com.ssafy.pcgg.domain.auth.dto.AuthLoginRequest;
import com.ssafy.pcgg.domain.auth.dto.AuthLoginResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public AuthLoginResponse login(HttpServletResponse response, AuthLoginRequest authLoginRequest) throws UnsupportedEncodingException {
        String email = authLoginRequest.getEmail();
        String password = authLoginRequest.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        // authenticate()가 실행되면, CustomUserDetailsService의 loadUserByEmail이 실행된다.
        // email에 맞지 않는 password를 입력할 시 에러를 던진다. // 이유는 잘 모르겠다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);

        // sameSite 에 대해 알아보고 사용할 것.
        // sameSite 설정을 위해 Cookie보다 ResponseCookie 사용
        // accessToken 을 cookie 에 저장하면 다음과 같은 문제가 발생
        // 1. 토큰이 "Set-Cookie: {name}=token" 형식으로 response 헤더에 저장
        // 2. 프론트는 Cookie 값을 request 마다 자동으로 보내게 되는데 "Authorization: token"이 아니라 "Cookie: {name}=token" 형식으로 보냄
        // 3. 프론트(vue)에서 request 를 정상적으로 보내기 위해 "token"을 추출해 사용해야 하는데 보안을 위해 걸어둔 httpOnly 로 인해 헤더에 접근 불가
        // 따라서, accessToken은 body에 refreshToken은 cookie에 전달하기로 결정
//        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
//                .maxAge(1 * 24 * 60 * 60)
//                .domain("localhost")
//                .path("/")
//                // sameSite("None") 와 secure(true)는 정책상 동반되어야 한다
//                .sameSite("None")
//                .secure(true)
//                .httpOnly(true)
//                .build();
//        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        return new AuthLoginResponse(accessToken);
    }
}
