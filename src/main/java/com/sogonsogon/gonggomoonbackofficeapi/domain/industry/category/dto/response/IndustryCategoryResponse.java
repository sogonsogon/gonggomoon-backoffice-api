package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.response;

//TODO 분석 개수 추가 해야 함..
public record IndustryCategoryResponse(
        Long industryCategoryId,
        String industryCategoryName
//        int analysisCount
) {
}
