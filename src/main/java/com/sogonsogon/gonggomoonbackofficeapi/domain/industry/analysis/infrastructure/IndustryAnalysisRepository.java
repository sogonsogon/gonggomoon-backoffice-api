package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.entity.IndustryAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndustryAnalysisRepository extends JpaRepository<IndustryAnalysis, Long> {

    List<IndustryAnalysis> findIndustryAnalysisByIndustryCategoryId(Long industryCategoryId);
}
