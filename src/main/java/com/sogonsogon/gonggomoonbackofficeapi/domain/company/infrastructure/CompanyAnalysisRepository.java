package com.sogonsogon.gonggomoonbackofficeapi.domain.company.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyAnalysisRepository extends JpaRepository<CompanyAnalysis, Long> {

    boolean existsCompanyAnalysisByCompanyName(String companyName);
}
