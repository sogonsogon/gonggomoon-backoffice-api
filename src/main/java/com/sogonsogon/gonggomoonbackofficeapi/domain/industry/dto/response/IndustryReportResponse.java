package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.IndustryReportStatus;

import java.time.Instant;
import java.util.List;

public record IndustryReportResponse(
        Long reportId,
        Long industryId,
        String industryName,
        IndustryReportStatus reportStatus,
        Integer reportYear,
        String competition,
        String marketSize,
        List<String> keyword,
        List<String> trend,
        List<String> regulation,
        List<String> hiring,
        List<String> investment,
        Instant createdAt,
        Instant updatedAt
) {
}
