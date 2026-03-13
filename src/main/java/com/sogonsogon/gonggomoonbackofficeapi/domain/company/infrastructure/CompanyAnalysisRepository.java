package com.sogonsogon.gonggomoonbackofficeapi.domain.company.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyAnalysis;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CompanyAnalysisRepository extends JpaRepository<CompanyAnalysis, Long> {

    boolean existsCompanyAnalysisByCompanyName(String companyName);

    /**
     * 조인을 사용하지 않고 Set을 이용하여 카테고리 정보만 DB에서 가져오고 Map 형태로 만들어 처리 예정
     */
    @Query("SELECT i, c.categoryName FROM CompanyAnalysis i " +
            "JOIN Industry c ON i.industryCategoryId = c.id " + // 매핑 없어도 ON으로 조인 가능
            "WHERE (:name IS NULL OR i.companyName LIKE %:name%) " +
            "AND (:industryType IS NULL OR c.categoryName = :industryType) " +
            "AND (:companyType IS NULL OR i.companyType = :companyType)")
    Page<Object[]> search(
            @Param("name") String name,
            @Param("industryType") IndustryType industryType,
            @Param("companyType") CompanyType companyType,
            Pageable pageable);
}
