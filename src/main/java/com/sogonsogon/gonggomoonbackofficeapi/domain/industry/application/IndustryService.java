package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.application;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.request.CreateIndustryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.request.UpdateIndustryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.Industry;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity.IndustryRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.error.IndustryErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IndustryService {

    private final IndustryRepository industryRepository;

    public IndustryService(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    /**
     * 산업 카테고리 생성
     * 동일한 이름의 카테고리 생성 방지 필요
     */
    @Transactional
    public void createIndustry(CreateIndustryRequest request,
                               Long userId) {

        if (industryRepository.existsByName(request.industryName())) throw new IllegalArgumentException();

        Industry newIndustry = Industry.create(request.industryName(), userId);

        industryRepository.save(newIndustry);
    }

    /**
     * 산업 카테고리 수정
     */
    @Transactional
    public void updateIndustryCategory(UpdateIndustryRequest request, Long id, Long userId) {

        if (industryRepository.existsByName(request.industryName())) throw new BaseException(IndustryErrorCode.INDUSTRY_DUPLICATE_NAME);

        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new BaseException(IndustryErrorCode.INDUSTRY_NOT_FOUND));

        industry.update(request.industryName(), userId);
    }

    /**
     * 산업 목록 조회
     */
    @Transactional(readOnly = true)
    public IndustryListResponse getIndustry() {

        List<IndustryResponse> responses = industryRepository.findAllWithReportCount();

        return new IndustryListResponse(responses);
    }

    @Transactional
    public void deleteIndustry(Long industryId) {

        Industry industry = industryRepository.findById(industryId)
                .orElseThrow(() -> new BaseException(IndustryErrorCode.INDUSTRY_NOT_FOUND));

        industryRepository.delete(industry);
    }
}
