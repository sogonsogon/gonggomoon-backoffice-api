package com.sogonsogon.gonggomoonbackofficeapi.domain.company.error;

import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum CompanyErrorCode implements BaseErrorCode {

    COMPANY_DUPLICATE_NAME("COMPANY_DUPLICATE_NAME", HttpStatus.CONFLICT, "이미 존재하는 이름입니다."),
    COMPANY_NOT_FOUND("COMPANY_NOT_FOUND", HttpStatus.NOT_FOUND, "존재하지 않은 기업입니다.")
    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    CompanyErrorCode(String code, HttpStatus status, String message) {
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
