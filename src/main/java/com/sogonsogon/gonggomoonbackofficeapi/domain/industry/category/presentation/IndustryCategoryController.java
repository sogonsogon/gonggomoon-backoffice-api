package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.application.IndustryCategoryService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.request.CreateIndustryCategoryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.request.UpdateIndustryCategoryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.global.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/industries")
public class IndustryCategoryController {

    private final IndustryCategoryService industryCategoryService;

    @Autowired
    public IndustryCategoryController(IndustryCategoryService industryCategoryService) {
        this.industryCategoryService = industryCategoryService;
    }

    @PostMapping
    public ResponseEntity<Void> createIndustryCategory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestBody @Valid CreateIndustryCategoryRequest request) {

        industryCategoryService.createIndustryCategory(request, Long.valueOf(userDetails.getUsername()));

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateIndustryCategory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestBody @Valid UpdateIndustryCategoryRequest request) {

        industryCategoryService.updateIndustryCategory(request, Long.valueOf(userDetails.getUsername()));

        return ResponseEntity.ok().build();
    }
}
