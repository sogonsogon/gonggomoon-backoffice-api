package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.response;

import java.util.List;

public record ReportResponse(
        Integer analysisYear,
        List<String> keyword,
        String marketSize,
        List<String> trend,
        List<String> regulation,
        String competition,
        List<String> hiring,
        List<String> investment
) {
}
