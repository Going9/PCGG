package com.ssafy.pcgg.domain.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER");

    private final String key;
}
