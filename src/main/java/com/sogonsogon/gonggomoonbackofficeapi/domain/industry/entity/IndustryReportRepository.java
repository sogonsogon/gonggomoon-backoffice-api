package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryReportResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IndustryReportRepository {

    Optional<IndustryReport> findById(Long industryReportId);

    List<IndustryReport> findByIndustryId(Long industryId);

    void resetOtherToPending(Long industryId, Long targetId);

    IndustryReport save(IndustryReport newIndustryReport);

    Optional<IndustryReportResponse> getIndustryReport(Long industryReportId);

    void delete(IndustryReport report);
}
