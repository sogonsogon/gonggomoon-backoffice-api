package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.request.CreateIndustryCategoryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.request.UpdateIndustryCategoryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.entity.IndustryCategory;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.infrastructure.IndustryCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IndustryCategoryService {

    private final IndustryCategoryRepository industryCategoryRepository;

    public IndustryCategoryService(IndustryCategoryRepository industryCategoryRepository) {
        this.industryCategoryRepository = industryCategoryRepository;
    }

    /**
     * 산업 카테고리 생성
     * 동일한 이름의 카테고리 생성 방지 필요
     */
    @Transactional
    public void createIndustryCategory(CreateIndustryCategoryRequest request, Long userId) {

        IndustryCategory newIndustryCategory = IndustryCategory.create(request.industryCategoryName(), userId);

        industryCategoryRepository.save(newIndustryCategory);
    }

    /**
     * 산업 카테고리 수정
     * 동일한 이름 수정 제약 필요?
     */
    @Transactional
    public void updateIndustryCategory(UpdateIndustryCategoryRequest request, Long userId) {

        IndustryCategory industryCategory = industryCategoryRepository.findById(request.industryCategoryId())
                .orElseThrow(IllegalArgumentException::new);

        industryCategory.update(request.industryCategoryName(), userId);
    }
}
