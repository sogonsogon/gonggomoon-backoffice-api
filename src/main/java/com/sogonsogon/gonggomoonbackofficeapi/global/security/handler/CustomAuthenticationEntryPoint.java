package com.sogonsogon.gonggomoonbackofficeapi.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.error.UserErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.global.response.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        UserErrorCode errorCode = (UserErrorCode) request.getAttribute("exception");

        if (errorCode == null) {
            errorCode = UserErrorCode.LOGIN_REQUIRED;
        }

        // 응답 타입을 JSON으로 설정
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getStatus().value());

        BaseResponse<Void> apiResponse = BaseResponse.fail(
                errorCode.getCode(),
                errorCode.getMessage()
        );


        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
