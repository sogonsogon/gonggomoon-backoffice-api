package com.sogonsogon.gonggomoonbackofficeapi.domain.post.error;

import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum PostSubmissionErrorCode implements BaseErrorCode {

    POST_SUBMISSION_NOT_FOUND("POST_SUBMISSION_NOT_FOUND", HttpStatus.NOT_FOUND, "존재하지 않은 게시 요청입니다."),
    POST_SUBMISSION_ALREADY_PROCESSED("POST_SUBMISSION_ALREADY_PROCESSED", HttpStatus.CONFLICT, "이미 처리된 요청입니다.")
    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    PostSubmissionErrorCode(String code, HttpStatus status, String message) {
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
