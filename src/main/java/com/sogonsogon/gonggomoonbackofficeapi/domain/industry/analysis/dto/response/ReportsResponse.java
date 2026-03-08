package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.response;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.entity.IndustryAnalysisStatus;

import java.time.Instant;

public record ReportsResponse(
        Long analysisId,
        Integer analysisYear,
        IndustryAnalysisStatus analysisStatus,
        Instant createdAt,
        Instant updatedAt
) {
}
