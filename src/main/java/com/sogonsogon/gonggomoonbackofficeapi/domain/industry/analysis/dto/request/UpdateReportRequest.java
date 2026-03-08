package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.request;

import java.util.List;

//TODO: 필드 값 논의 필요
public record UpdateReportRequest(

        Long categoryId,

        Integer analysisYear,

        String competition,

        String marketSize,

        List<String> keyword,

        List<String> trend,

        List<String> regulation,

        List<String> hiring,

        List<String> investment
) {
}
