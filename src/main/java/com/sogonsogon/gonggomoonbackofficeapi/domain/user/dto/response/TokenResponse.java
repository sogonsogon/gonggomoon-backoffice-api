package com.sogonsogon.gonggomoonbackofficeapi.domain.user.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
        String grantType,
        String accessToken,
        String refreshToken
) {}
