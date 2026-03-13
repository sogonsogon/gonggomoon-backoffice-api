package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.Industry;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.IndustryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndustryJpaRepository extends JpaRepository<Industry, Long>, IndustryRepository {

    @Query("""
        SELECT new com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryResponse(
            i.id,
            i.name,
            COUNT(ir)
        )
        FROM Industry i
        LEFT JOIN IndustryReport ir ON ir.industryId = i.id
        GROUP BY i.id, i.name
    """)
    List<IndustryResponse> findAllWithReportCount();
}
