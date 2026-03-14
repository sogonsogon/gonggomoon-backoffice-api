package com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;

public record CompanyListResponse(
        Long companyId,
        Long industryId,
        String companyName,
        String industryName,
        CompanyType companyType,
        Integer employeeCount,
        Integer foundedYear
) {
}
