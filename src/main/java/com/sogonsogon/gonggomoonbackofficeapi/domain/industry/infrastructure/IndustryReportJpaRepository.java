package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryReportResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.IndustryReport;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.IndustryReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IndustryReportJpaRepository extends JpaRepository<IndustryReport, Long>, IndustryReportRepository {

    @Modifying(clearAutomatically = true) // 퀴리 실행 후 영속성 컨텍스트를 비워줌
    @Query("UPDATE IndustryReport i SET i.status = 'PENDING' " +
            "WHERE i.industryId = :industryId " +
            "AND i.status = 'PUBLISHED' " +
            "AND i.id != :targetId")
    void resetOtherToPending(@Param("industryId") Long industryId, @Param("targetId") Long targetId);

    @Query(
            value = """
                SELECT new com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryReportResponse(
                    ir.id,
                    i.id,
                    i.name,
                    ir.status,
                    ir.reportYear,
                    ir.competition,
                    ir.marketSize,
                    ir.keyword,
                    ir.trend,
                    ir.regulation,
                    ir.hiring,
                    ir.investment,
                    ir.createdAt,
                    ir.updatedAt
                )
                FROM IndustryReport ir
                JOIN Industry i ON i.id = ir.industryId
                WHERE ir.id = :id
        """
    )
    Optional<IndustryReportResponse> getIndustryReport(
            @Param("id") Long industryReportId
    );
}
