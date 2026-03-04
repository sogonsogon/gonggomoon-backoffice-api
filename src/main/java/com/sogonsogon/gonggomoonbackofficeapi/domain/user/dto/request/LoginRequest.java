package com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotBlank(message = "비밀번호 필수 입력값입니다.")
        @Size(min = 6, message = "비밀번호는 최소 6글자 이상입니다.")
        String password
) {
}
