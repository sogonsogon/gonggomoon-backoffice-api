package com.sogonsogon.gonggomoonbackofficeapi.domain.company.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.Company;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CompanyJpaRepository extends JpaRepository<Company, Long>, CompanyRepository {

    @Query(
            value = """
                SELECT new com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyListResponse(
                    c.id,
                    i.id,
                    c.name,
                    i.name,
                    c.type,
                    c.employeeCount,
                    c.foundedYear
                )
                FROM Company c
                JOIN Industry i ON i.id = c.industryId
                WHERE (:name IS NULL OR c.name LIKE %:name%)
                AND (:industryId IS NULL OR c.industryId = :industryId)
                AND (:companyType IS NULL OR c.type = :companyType)
        """,
            countQuery = """
            SELECT COUNT(c)
            FROM Company c
            JOIN Industry i ON i.id = c.industryId
            WHERE (:name IS NULL OR c.name LIKE %:name%)
            AND (:industryId IS NULL OR c.industryId = :industryId)
            AND (:companyType IS NULL OR c.type = :companyType)
        """
    )
    Page<CompanyListResponse> searchCompanies(
            @Param("name") String name,
            @Param("industryId") Long industryId,
            @Param("companyType") CompanyType companyType,
            Pageable pageable);
}
