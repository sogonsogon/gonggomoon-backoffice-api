package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateIndustryCategoryRequest(
        @NotBlank(message = "카테고리 이름을 채워주세요.")
        String industryCategoryName
) {
}
