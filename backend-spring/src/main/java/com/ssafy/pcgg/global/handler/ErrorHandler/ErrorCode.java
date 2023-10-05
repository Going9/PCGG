package com.ssafy.pcgg.global.handler.ErrorHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_SEND_ERROR(404, "이메일 전송 실패"),
    REDIS_ERROR(500, "레디스 에러"),
    EMAIL_NOT_FOUND(404, "존재하지 않는 이메일 입니다."),
    PWD_NOT_MATCH(404, "비밀번호가 일치하지 않습니다."),
    EMAIL_DUPLICATE(409, "이메일이 이미 사용중입니다."),
    NICKNAME_DUPLICATE(409, "닉네임이 이미 사용중입니다."),
    EMAIL_ERROR(404, "EMAIL_ERROR"),
    EMAIL_CODE_ERROR(404, "EMAIL_CODE_ERROR");

    private int status;
    private String message;
}
