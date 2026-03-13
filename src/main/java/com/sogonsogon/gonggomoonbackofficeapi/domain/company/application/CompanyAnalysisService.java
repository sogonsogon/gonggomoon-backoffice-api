package com.sogonsogon.gonggomoonbackofficeapi.domain.company.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyReportResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyReportsResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CreateCompanyReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.UpdateCompanyReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyAnalysis;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.infrastructure.CompanyAnalysisRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.Industry;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.infrastructure.IndustryJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyAnalysisService {

    private final CompanyAnalysisRepository companyAnalysisRepository;
    public final IndustryJpaRepository industryJpaRepository;

    public CompanyAnalysisService(CompanyAnalysisRepository companyAnalysisRepository, IndustryJpaRepository industryJpaRepository) {
        this.companyAnalysisRepository = companyAnalysisRepository;
        this.industryJpaRepository = industryJpaRepository;
    }

    public void create(CreateCompanyReportRequest request, Long userId) {

        if (!companyAnalysisRepository.existsCompanyAnalysisByCompanyName(request.companyName())) throw new IllegalArgumentException();

        CompanyAnalysis analysis = CompanyAnalysis.create(request.industryCategoryId(), request.companyName(), request.companyType(), request.employeeCount(),
                request.address(), request.revenue(), request.foundedYear(), request.websiteUrl(), request.description(), userId);

        companyAnalysisRepository.save(analysis);
    }

    public void update(UpdateCompanyReportRequest request, Long companyReportId, Long userId) {

        CompanyAnalysis analysis = companyAnalysisRepository.findById(companyReportId).orElseThrow(IllegalArgumentException::new);

        analysis.update(request.industryCategoryId(), request.companyName(), request.companyType(), request.employeeCount(),
                request.address(), request.revenue(), request.foundedYear(), request.websiteUrl(), request.description(), userId);
    }

    public Page<CompanyReportsResponse> getCompanyReports(String name, IndustryType industryType, CompanyType companyType, Pageable pageable) {

        Page<Object[]> companyAnalysisPage = companyAnalysisRepository.search(name, industryType, companyType, pageable);

        return companyAnalysisPage.map(result -> {
            CompanyAnalysis companyAnalysis = (CompanyAnalysis) result[0];
            String categoryName = (String) result[1];

            return CompanyReportsResponse.from(companyAnalysis, categoryName);
        });
    }

    public CompanyReportResponse getCompanyReport(Long id) {

        CompanyAnalysis analysis = companyAnalysisRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Industry industry = industryJpaRepository.findById(analysis.getIndustryCategoryId()).orElseThrow(IllegalArgumentException::new);

        return new CompanyReportResponse(analysis.getId(), analysis.getCompanyType(), IndustryType.valueOf(industry.getCategoryName()),
                analysis.getEmployeeCount(), analysis.getDescription(), analysis.getAddress(), analysis.getRevenue(), analysis.getFoundedYear(),
                analysis.getWebsiteUrl(), analysis.getCreatedBy(), analysis.getUpdatedBy(), analysis.getCreatedAt(), analysis.getUpdatedAt());
    }

    /**
     * softDelete 고민 중..
     */
    public void deleteCompanyReport(Long id) {

        if (!companyAnalysisRepository.existsById(id)) throw new IllegalArgumentException();

        companyAnalysisRepository.deleteById(id);
    }
}
