package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.request.CreateReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.request.UpdateReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.response.ReportResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.response.ReportsResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.entity.IndustryAnalysis;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.infrastructure.IndustryAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class IndustryAnalysisService {

    private final IndustryAnalysisRepository industryAnalysisRepository;

    public IndustryAnalysisService(IndustryAnalysisRepository industryAnalysisRepository) {
        this.industryAnalysisRepository = industryAnalysisRepository;
    }

    /**
     * 산업 분석 생성
     * 제약조건 고민 필요
     * 카테고리 id가 없는 경우
     */
    @Transactional
    public void createReport(CreateReportRequest request, Long categoryId, Long userId) {

        IndustryAnalysis newIndustryAnalysis = IndustryAnalysis.create(categoryId, request.analysisYear(), userId, request.competition(),
                request.marketSize(), request.trend(), request.regulation(), request.keyword(), request.hiring(), request.investment());

        industryAnalysisRepository.save(newIndustryAnalysis);
    }

    /**
     * 산업 분석 수정
     * 정책 및 기능 flow 내용 FE와 상의 해봐야 함
     */
    @Transactional
    public void updateReport(UpdateReportRequest request, Long industryId, Long userId) {

        IndustryAnalysis industryAnalysis = industryAnalysisRepository.findById(industryId).orElseThrow(IllegalArgumentException::new);

        industryAnalysis.update(request.categoryId(), request.analysisYear(), userId, request.competition(), request.marketSize(),
                request.trend(), request.regulation(), request.keyword(), request.hiring(), request.investment());
    }

    /**
     * 해당 산업 카테고리 내 분석 목록 조회
     */
    @Transactional(readOnly = true)
    public Map<String, List<ReportsResponse>> getReports(Long id) {

        List<IndustryAnalysis> analyses = industryAnalysisRepository.findIndustryAnalysisByIndustryCategoryId(id);

        List<ReportsResponse> responses = analyses.stream()
                .map(analysis -> new ReportsResponse(
                        analysis.getId(),
                        analysis.getAnalysis_year(),
                        analysis.getStatus(),
                        analysis.getCreatedAt(),
                        analysis.getUpdatedAt()
                )).toList();

        return Map.of("content", responses);
    }

    /**
     * 분석 단건 조회
     */
    @Transactional(readOnly = true)
    public ReportResponse getReport(Long id) {

        IndustryAnalysis analysis = industryAnalysisRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return new ReportResponse(analysis.getAnalysis_year(), analysis.getKeyword(), analysis.getMarketSize(),
                analysis.getTrend(), analysis.getRegulation(), analysis.getCompetition(), analysis.getHiring(), analysis.getInvestment());
    }

    /**
     * 분석 발행
     * 누가 퍼블리시 한지 남겨야 하나?
     */
    @Transactional
    public void publishReport(Long id) {

        IndustryAnalysis analysis = industryAnalysisRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        industryAnalysisRepository.resetOtherToPending(analysis.getIndustryCategoryId(), id);

        analysis.publish();
    }

    /**
     * 분석 삭제
     * 아.. 누가 삭제한지 남겨야 할까?
     * soft delete? 기업이 다 들고 있을텐데..
     */
    @Transactional
    public void deleteReport(Long id) {

        if (!industryAnalysisRepository.existsById(id)) throw new IllegalArgumentException();

        industryAnalysisRepository.deleteById(id);
    }

}
