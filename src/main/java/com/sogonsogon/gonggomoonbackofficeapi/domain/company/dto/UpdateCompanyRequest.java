package com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;

public record UpdateCompanyRequest(
        Long industryId,
        String name,
        CompanyType companyType,
        Integer employeeCount,
        String address,
        Integer foundedYear,
        String url,
        String description
) {
}
