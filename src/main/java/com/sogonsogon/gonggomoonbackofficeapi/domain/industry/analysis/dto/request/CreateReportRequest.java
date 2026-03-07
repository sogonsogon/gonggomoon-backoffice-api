package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.request;

import java.util.List;

/**
 * 인자의 형태와 NotNull 여부 논의 필요
 */
public record CreateReportRequest(

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
