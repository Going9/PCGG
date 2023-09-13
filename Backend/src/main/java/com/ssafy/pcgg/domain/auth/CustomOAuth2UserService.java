package com.ssafy.pcgg.domain.auth;

import com.ssafy.pcgg.domain.auth.dto.OAuthAttributeDto;
import com.ssafy.pcgg.domain.auth.dto.SessionUserDto;
import com.ssafy.pcgg.domain.user.UserEntity;
import com.ssafy.pcgg.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributeDto attributeDto = OAuthAttributeDto.of(registrationId, userNameAttributeName, oauth2User.getAttributes());

        UserEntity userEntity = saveOrUpdate(attributeDto);
        httpSession.setAttribute("user", new SessionUserDto(userEntity));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributeDto.getAttributes(), attributeDto.getNameAttributeKey());
    }

    private UserEntity saveOrUpdate(OAuthAttributeDto attributeDto) {
        UserEntity userEntity = userRepository.findByEmail(attributeDto.getEmail())
//                .map(entity -> entity.update(attributeDto.getName(), attributeDto.getPicture()))
                .orElse(attributeDto.toEntity());

        return userRepository.save(userEntity);
    }
}
