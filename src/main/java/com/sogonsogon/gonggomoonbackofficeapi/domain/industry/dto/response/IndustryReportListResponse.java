package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.IndustryReportStatus;

import java.time.Instant;
import java.util.List;

public record IndustryReportListResponse(
        Long industryId,
        String industryName,
        List<Item> contents
) {
    public record Item(
            Long reportId,
            Integer reportYear,
            IndustryReportStatus reportStatus,
            Instant createdAt,
            Instant updatedAt
    ) {
    }
}



