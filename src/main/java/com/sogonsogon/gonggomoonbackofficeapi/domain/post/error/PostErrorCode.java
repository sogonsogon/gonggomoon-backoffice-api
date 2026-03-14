package com.sogonsogon.gonggomoonbackofficeapi.domain.post.error;

import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements BaseErrorCode {
    POST_NOT_FOUND("POST_NOT_FOUND", HttpStatus.NOT_FOUND, "존재하지 않은 공고입니다."),
    DUPLICATE_POST_URL("DUPLICATE_POST_URL", HttpStatus.CONFLICT, "이미 등록된 공고 URL입니다."),
    INVALID_DATE_RANGE("INVALID_DATE_RANGE", HttpStatus.BAD_REQUEST, "시작일은 마감일보다 이전이어야 합니다."),
    POST_NOT_ANALYZED("POST_NOT_ANALYZED", HttpStatus.CONFLICT, "분석이 완료되지 않은 공고입니다.")
    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    PostErrorCode(String code, HttpStatus status, String message) {
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
