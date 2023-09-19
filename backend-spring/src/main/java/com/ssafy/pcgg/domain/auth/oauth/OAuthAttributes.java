package com.ssafy.pcgg.domain.auth.oauth;

import com.ssafy.pcgg.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttrivuteName,
                                     Map<String, Object> atrributes) {
//        if (socialType == SocialType.NAVER) {
//            return ofNaver(userNameAttrivuteName, atrributes);
//        }
//        if (socialType == SocialType.KAKAO) {
//            return ofKakao(userNameAttrivuteName, atrributes);
//        }
        return ofGoogle(userNameAttrivuteName, atrributes);
    }
//    public static OAuthAttributes ofNaver(String userNameAttributeName,
//                                            Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .nameAttributeKey(userNameAttributeName)
//                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
//                .build();
//    }

//    public static OAuthAttributes ofKakao(String userNameAttributeName,
//                                           Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .nameAttributeKey(userNameAttributeName)
//                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
//                .build();
//    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName,
                                           Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public UserEntity toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return UserEntity.builder()
                .email(UUID.randomUUID() + "@socialUser.com")
                .build();
    }
}
