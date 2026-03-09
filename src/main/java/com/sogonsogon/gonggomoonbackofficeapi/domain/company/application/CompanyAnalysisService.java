package com.sogonsogon.gonggomoonbackofficeapi.domain.company.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CreateCompanyReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.UpdateCompanyReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyAnalysis;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.infrastructure.CompanyAnalysisRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyAnalysisService {

    private final CompanyAnalysisRepository companyAnalysisRepository;

    public CompanyAnalysisService(CompanyAnalysisRepository companyAnalysisRepository) {
        this.companyAnalysisRepository = companyAnalysisRepository;
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
}
