package com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyAnalysis;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.entity.IndustryType;

public record CompanyReportsResponse(
        Long companyId,
        String companyName,
        IndustryType industryType,
        CompanyType companyType,
        Integer employeeCount,
        Integer foundedYear
) {
    public static CompanyReportsResponse from(CompanyAnalysis analysis, String industryType) {
        return new CompanyReportsResponse(
                analysis.getId(),
                analysis.getCompanyName(),
                IndustryType.valueOf(industryType),
                analysis.getCompanyType(),
                analysis.getEmployeeCount(),
                analysis.getFoundedYear()
        );
    }
}
