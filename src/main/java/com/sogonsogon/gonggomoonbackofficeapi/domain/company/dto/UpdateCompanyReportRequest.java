package com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;

public record UpdateCompanyReportRequest(
        Long industryCategoryId,
        String companyName,
        CompanyType companyType,
        Integer employeeCount,
        String address,
        Long revenue,
        Integer foundedYear,
        String websiteUrl,
        String description
) {
}
