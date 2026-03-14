package com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;

import java.time.Instant;

public record CompanyResponse(
        Long companyId,
        Long industryId,
        String companyName,
        CompanyType companyType,
        String industryName,
        String url,
        Integer foundedYear,
        String address,
        Integer employeeCount,
        String description,
        Long createdBy,
        Long updatedBy,
        Instant createdAt,
        Instant updatedAt
) {
}
