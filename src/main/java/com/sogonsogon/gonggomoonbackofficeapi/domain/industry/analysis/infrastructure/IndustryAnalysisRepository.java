package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.entity.IndustryAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndustryAnalysisRepository extends JpaRepository<IndustryAnalysis, Long> {

    List<IndustryAnalysis> findIndustryAnalysisByIndustryCategoryId(Long industryCategoryId);

    @Modifying(clearAutomatically = true) // 퀴리 실행 후 영속성 컨텍스트를 비워줌
    @Query("UPDATE IndustryAnalysis i SET i.status = 'PENDING' " +
            "WHERE i.industryCategoryId = :industryId " +
            "AND i.status = 'PUBLISHED' " +
            "AND i.id != :targetId")
    void resetOtherToPending(@Param("industryId") Long industryId, @Param("targetId") Long targetId);
}
