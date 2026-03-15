package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.error;

import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum IndustryReportErrorCode implements BaseErrorCode {

    INDUSTRY_REPORT_NOT_FOUND("INDUSTRY_REPORT_NOT_FOUND", HttpStatus.NOT_FOUND, "존재하지 않은 산업 보고서입니다."),
    INVALID_REPORT_YEAR("INVALID_REPORT_YEAR", HttpStatus.BAD_REQUEST, "현재 연도보다 미래의 보고서는 등록할 수 없습니다.")
    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    IndustryReportErrorCode(String code, HttpStatus status, String message) {
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
