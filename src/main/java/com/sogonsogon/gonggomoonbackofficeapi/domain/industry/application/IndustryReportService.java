package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.request.CreateIndustryReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryReportResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryReportListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.IndustryReport;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.IndustryReportRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.IndustryRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.Industry;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.error.IndustryReportErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class IndustryReportService {

    private final IndustryRepository industryRepository;
    private final IndustryReportRepository industryReportRepository;

    public IndustryReportService(IndustryRepository industryRepository, IndustryReportRepository industryReportRepository) {
        this.industryRepository = industryRepository;
        this.industryReportRepository = industryReportRepository;
    }

    /**
     * 산업 분석 생성
     * 제약조건 고민 필요
     * 카테고리 id가 없는 경우
     */
    @Transactional
    public void createReport(CreateIndustryReportRequest request, Long industryId, Long userId) {

        if (!industryRepository.existsById(industryId)) throw new BaseException(IndustryReportErrorCode.INDUSTRY_REPORT_NOT_FOUND);

        if (request.reportYear() > LocalDate.now().getYear()) throw new BaseException(IndustryReportErrorCode.INVALID_REPORT_YEAR);

        IndustryReport newIndustryReport = IndustryReport.create(
                industryId,
                request.reportYear(),
                request.competition(),
                request.marketSize(),
                request.trend(),
                request.regulation(),
                request.keyword(),
                request.hiring(),
                request.investment(),
                userId
                );

        industryReportRepository.save(newIndustryReport);
    }

    /**
     * 해당 산업 카테고리 내 분석 목록 조회
     */
    @Transactional(readOnly = true)
    public IndustryReportListResponse getReports(Long industryId) {

        Industry industry = industryRepository.findById(industryId)
                .orElseThrow(() -> new BaseException(IndustryReportErrorCode.INDUSTRY_REPORT_NOT_FOUND));

        List<IndustryReport> reports = industryReportRepository.findByIndustryId(industryId);

        List<IndustryReportListResponse.Item> items = reports.stream()
                .map(report -> new IndustryReportListResponse.Item(
                        report.getId(),
                        report.getReportYear(),
                        report.getStatus(),
                        report.getCreatedAt(),
                        report.getUpdatedAt()
                )).toList();

        return new IndustryReportListResponse(
                industry.getId(),
                industry.getName(),
                items
        );
    }

    /**
     * 분석 단건 조회
     */
    @Transactional(readOnly = true)
    public IndustryReportResponse getReport(Long industryReportId) {

        IndustryReportResponse response = industryReportRepository.getIndustryReport(industryReportId)
                .orElseThrow(() -> new BaseException(IndustryReportErrorCode.INDUSTRY_REPORT_NOT_FOUND));

        return response;
    }

    /**
     * 분석 발행
     */
    @Transactional
    public void publishReport(Long industryReportId, Long publishedBy) {

        IndustryReport report = industryReportRepository.findById(industryReportId)
                .orElseThrow(() -> new BaseException(IndustryReportErrorCode.INDUSTRY_REPORT_NOT_FOUND));

        industryReportRepository.resetOtherToPending(report.getIndustryId(), industryReportId);

        IndustryReport updatedReport = industryReportRepository.findById(industryReportId)
                .orElseThrow(() -> new BaseException(IndustryReportErrorCode.INDUSTRY_REPORT_NOT_FOUND));

        updatedReport.publish(publishedBy);
    }

    /**
     * 분석 삭제
     */
    @Transactional
    public void deleteReport(Long id) {

        IndustryReport report = industryReportRepository.findById(id)
                .orElseThrow(() -> new BaseException(IndustryReportErrorCode.INDUSTRY_REPORT_NOT_FOUND));

        industryReportRepository.delete(report);
    }
}
