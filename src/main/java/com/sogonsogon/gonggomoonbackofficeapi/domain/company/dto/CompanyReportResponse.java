package com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.entity.IndustryType;

import java.time.Instant;

public record CompanyReportResponse(
        Long companyId,
        CompanyType companyType,
        IndustryType industryType,
        Integer employeeCount,
        String description,
        String address,
        Long revenue,
        Integer foundedYear,
        String websiteUrl,
        Long createdBy,
        Long updatedBy,
        Instant createdAt,
        Instant updatedAt
) {
}
