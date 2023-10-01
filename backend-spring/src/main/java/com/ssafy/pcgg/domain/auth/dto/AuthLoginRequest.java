package com.ssafy.pcgg.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthLoginRequest {

    @NotNull
    @Schema(description = "아이디(이메일)" , example = "ssafy1")
    private String email;

    @NotNull
    @Schema(description = "비밀번호" , example = "ssafy")
    private String password;
}
