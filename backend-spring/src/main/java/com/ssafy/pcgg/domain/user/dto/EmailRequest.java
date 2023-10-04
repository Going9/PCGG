package com.ssafy.pcgg.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailRequest {

    private String email;
    private String authCode;
}
