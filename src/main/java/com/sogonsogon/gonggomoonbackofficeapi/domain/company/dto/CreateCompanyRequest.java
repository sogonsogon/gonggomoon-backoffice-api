package com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;

/**
 * 검증 로직 고민
 */
public record CreateCompanyRequest(
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
