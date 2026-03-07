package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.entity.IndustryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustryCategoryRepository extends JpaRepository<IndustryCategory, Long> {
}
