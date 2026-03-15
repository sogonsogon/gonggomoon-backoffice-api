package com.sogonsogon.gonggomoonbackofficeapi.domain.company.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CreateCompanyRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.UpdateCompanyRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.Company;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.error.CompanyErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.Industry;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.IndustryRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.error.IndustryErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    public final IndustryRepository industryRepository;

    public CompanyService(CompanyRepository companyRepository, IndustryRepository industryRepository) {
        this.companyRepository = companyRepository;
        this.industryRepository = industryRepository;
    }

    @Transactional
    public void create(CreateCompanyRequest request, Long userId) {

        if (companyRepository.existsCompanyByName(request.name())) throw new BaseException(CompanyErrorCode.COMPANY_DUPLICATE_NAME);

        Company company = Company.create(
                request.industryId(),
                request.name(),
                request.companyType(),
                request.employeeCount(),
                request.address(),
                request.description(),
                request.foundedYear(),
                request.url(),
                userId
        );

        companyRepository.save(company);
    }

    @Transactional
    public void update(UpdateCompanyRequest request, Long companyId, Long userId) {

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new BaseException(CompanyErrorCode.COMPANY_NOT_FOUND));

        company.update(
                request.industryId(),
                request.name(),
                request.companyType(),
                request.employeeCount(),
                request.address(),
                request.description(),
                request.foundedYear(),
                request.url(),
                userId
        );
    }

    @Transactional(readOnly = true)
    public Page<CompanyListResponse> getCompanies(String name, Long industryId, CompanyType companyType, Pageable pageable) {

        Page<CompanyListResponse> responses = companyRepository.searchCompanies(name, industryId, companyType, pageable);

        return responses;
    }

    public CompanyResponse getCompany(Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BaseException(CompanyErrorCode.COMPANY_NOT_FOUND));

        Industry industry = industryRepository.findById(company.getIndustryId())
                .orElseThrow(() -> new BaseException(IndustryErrorCode.INDUSTRY_NOT_FOUND));

        return new CompanyResponse(
                company.getId(),
                industry.getId(),
                company.getName(),
                company.getType(),
                industry.getName(),
                company.getUrl(),
                company.getFoundedYear(),
                company.getAddress(),
                company.getEmployeeCount(),
                company.getDescription(),
                company.getCreatedBy(),
                company.getUpdatedBy(),
                company.getCreatedAt(),
                company.getUpdatedAt()
        );
    }

    /**
     * softDelete 고민 중..
     */
    public void deleteCompany(Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BaseException(CompanyErrorCode.COMPANY_NOT_FOUND));


        companyRepository.delete(company);
    }
}
