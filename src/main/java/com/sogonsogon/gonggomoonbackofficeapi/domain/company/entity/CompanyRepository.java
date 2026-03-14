package com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CompanyRepository {

    boolean existsCompanyByName(String name);

    Company save(Company company);

    Optional<Company> findById(Long companyId);

    Page<CompanyListResponse> searchCompanies(
            String name,
            Long industryId,
            CompanyType companyType,
            Pageable pageable);

    void delete(Company company);
}
