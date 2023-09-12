package com.ssafy.pcgg.domain.user.dto;

import com.ssafy.pcgg.domain.user.UserEntity;
import lombok.Data;

@Data
public class UserListResponse {

    private long userId;
    private String email;
    private String password;
    public UserListResponse(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
    }

}
