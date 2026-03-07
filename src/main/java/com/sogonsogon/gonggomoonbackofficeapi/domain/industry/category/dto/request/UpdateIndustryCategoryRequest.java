package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateIndustryCategoryRequest(

        @NotNull(message = "카테고리 id가 없습니다.")
        Long industryCategoryId,

        @NotBlank(message = "카테고리 이름을 채워주세요.")
        String industryCategoryName
) {
}
