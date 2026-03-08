package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.request.CreateReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.request.UpdateReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.entity.IndustryAnalysis;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.infrastructure.IndustryAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IndustryAnalysisService {

    private final IndustryAnalysisRepository industryAnalysisRepository;

    public IndustryAnalysisService(IndustryAnalysisRepository industryAnalysisRepository) {
        this.industryAnalysisRepository = industryAnalysisRepository;
    }

    /**
     * 산업 분석 생성
     * 제약조건 고민 필요
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
}
