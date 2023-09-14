package com.ssafy.pcgg.domain.auth.oauth;

import com.ssafy.pcgg.domain.auth.Role;
import com.ssafy.pcgg.domain.auth.TokenProvider;
import com.ssafy.pcgg.domain.user.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Value("${jwt.header}")
    private String accessHeader;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Success OAuth2 Login!");

        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            if (oAuth2User.getRole() == Role.GUEST) {
                String accessToken = tokenProvider.createToken(authentication);
//                response.addHeader();
                response.sendRedirect("oauth2/sign-up");

            } else {
                loginSuccess(response, oAuth2User, authentication);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User, Authentication authentication) throws IOException {
        String accessToken = tokenProvider.createToken(authentication);
        System.out.println(accessToken);
        response.addHeader(accessHeader, "Bearer" + accessToken);
    }
}
