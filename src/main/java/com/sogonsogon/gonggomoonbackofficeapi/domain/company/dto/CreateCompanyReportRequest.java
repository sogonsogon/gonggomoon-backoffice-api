package com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;

/**
 * 검증 로직 고민
 * EnumType 검증용 어노테이션 생성?
 */
public record CreateCompanyReportRequest(
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
