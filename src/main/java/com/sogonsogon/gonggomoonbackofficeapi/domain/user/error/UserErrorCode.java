package com.sogonsogon.gonggomoonbackofficeapi.domain.user.error;

import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements BaseErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "존재하지 않은 유저입니다."),
    LOGIN_REQUIRED("LOGIN_REQUIRED", HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", HttpStatus.FORBIDDEN, "본인의 토큰만 갱신할 수 있습니다."),
    INVALID_TOKEN("INVALID_TOKEN", HttpStatus.UNAUTHORIZED, "토큰 정보가 일치하지 않습니다. 다시 로그인해주세요.")    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    UserErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
