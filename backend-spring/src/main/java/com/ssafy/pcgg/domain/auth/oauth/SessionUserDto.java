package com.ssafy.pcgg.domain.auth.oauth;

import com.ssafy.pcgg.domain.user.UserEntity;
import lombok.Getter;

@Getter
public class SessionUserDto {

//    private String name;
    private String email;
//    private String picture;

    public SessionUserDto(UserEntity userEntity) {
//        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
//        this.picture = user.getPicture();
    }
}
