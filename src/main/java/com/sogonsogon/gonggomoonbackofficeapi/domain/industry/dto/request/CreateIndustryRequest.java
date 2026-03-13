package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateIndustryRequest(
        @NotBlank(message = "카테고리 이름을 채워주세요.")
        String industryName
) {
}
