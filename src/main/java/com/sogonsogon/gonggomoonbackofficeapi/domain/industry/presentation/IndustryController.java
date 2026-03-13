package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.application.IndustryService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.request.CreateIndustryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.request.UpdateIndustryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.global.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/industries")
public class IndustryController {

    private final IndustryService industryService;

    public IndustryController(IndustryService industryService) {
        this.industryService = industryService;
    }

    /**
     * 산업 생성
     */
    @PostMapping
    public ResponseEntity<Void> createIndustryCategory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestBody @Valid CreateIndustryRequest request) {

        industryService.createIndustry(request, Long.valueOf(userDetails.getUsername()));

        return ResponseEntity.ok().build();
    }

    /**
     * 산업 목록 조회
     */
    @GetMapping
    public ResponseEntity<IndustryListResponse> getIndustryCategories() {

        return ResponseEntity.ok(industryService.getIndustry());
    }

    /**
     * 산업 수정
     */
    @PatchMapping("/{industryId}")
    public ResponseEntity<Void> updateIndustryCategory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestBody @Valid UpdateIndustryRequest request,
                                                       @PathVariable Long industryId) {

        industryService.updateIndustryCategory(request, industryId, Long.valueOf(userDetails.getUsername()));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{industryId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long industryId) {

        industryService.deleteIndustry(industryId);

        return ResponseEntity.ok().build();
    }
}
