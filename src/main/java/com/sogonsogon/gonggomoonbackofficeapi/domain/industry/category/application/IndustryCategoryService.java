package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.request.CreateIndustryCategoryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.request.UpdateIndustryCategoryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.response.IndustryCategoryResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.entity.IndustryCategory;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.infrastructure.IndustryCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    public void updateIndustryCategory(UpdateIndustryCategoryRequest request,Long id, Long userId) {

        IndustryCategory industryCategory = industryCategoryRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        industryCategory.update(request.industryCategoryName(), userId);
    }

    /**
     * 카테고리 목록 조회
     * content로 감싸는게 dto에 있어야 할 듯?
     * List로 만드는거 까지?
     * 생성일 기준 오름차순 정렬
     */
    @Transactional(readOnly = true)
    public Map<String, List<IndustryCategoryResponse>> getIndustryCategorise() {

        List<IndustryCategory> categories = industryCategoryRepository.findAllByOrderByCreatedAtAsc();

        List<IndustryCategoryResponse> categoryResponses = categories.stream()
                .map(category -> new IndustryCategoryResponse(
                        category.getId(),
                        category.getCategoryName()
                ))
                .toList();

        return Map.of("content", categoryResponses);
    }
}
