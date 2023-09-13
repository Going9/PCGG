package com.ssafy.pcgg.domain.auth.dto;

import com.ssafy.pcgg.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributeDto {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
//    private String name;
    private String email;
//    private String picture;

    @Builder
    public OAuthAttributeDto(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
//        this.name = name;
        this.email = email;
//        this.picture = picture;
    }

    public static OAuthAttributeDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributeDto ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributeDto.builder()
//                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
//                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(email)
                .build();
    }
}
