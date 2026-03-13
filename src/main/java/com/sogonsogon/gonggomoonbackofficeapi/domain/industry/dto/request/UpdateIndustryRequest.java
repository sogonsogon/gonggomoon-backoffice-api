package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateIndustryRequest(

        @NotBlank(message = "산업 이름을 채워주세요.")
        String industryName
) {
}
