package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.error;

import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum IndustryErrorCode implements BaseErrorCode {

    INDUSTRY_NOT_FOUND("INDUSTRY_NOT_FOUND", HttpStatus.NOT_FOUND, "존재하지 않은 산업입니다."),
    INDUSTRY_DUPLICATE_NAME("INDUSTRY_DUPLICATE_NAME", HttpStatus.CONFLICT, "이미 존재하는 산업 이름입니다."),

    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    IndustryErrorCode(String code, HttpStatus status, String message) {
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
