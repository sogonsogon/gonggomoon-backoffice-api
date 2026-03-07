package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.application.IndustryCategoryService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.request.CreateIndustryCategoryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.request.UpdateIndustryCategoryRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.dto.response.IndustryCategoryResponse;
import com.sogonsogon.gonggomoonbackofficeapi.global.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateIndustryCategory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestBody @Valid UpdateIndustryCategoryRequest request,
                                                       @PathVariable Long id) {

        industryCategoryService.updateIndustryCategory(request, id, Long.valueOf(userDetails.getUsername()));

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, List<IndustryCategoryResponse>>> getIndustryCategories() {

        return ResponseEntity.ok(industryCategoryService.getIndustryCategorise());

    }
}
