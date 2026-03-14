package com.sogonsogon.gonggomoonbackofficeapi.domain.company.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.application.CompanyService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CreateCompanyRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.UpdateCompanyRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;
import com.sogonsogon.gonggomoonbackofficeapi.global.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/admin/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createCompanyReport(@RequestBody @Valid CreateCompanyRequest request,
                                                    @AuthenticationPrincipal UserDetails details) {

        companyService.create(request, Long.valueOf(details.getUsername()));

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success());
    }

    @PatchMapping("/{companyId}")
    public ResponseEntity<BaseResponse<Void>> updateCompanyReport(@RequestBody @Valid UpdateCompanyRequest request,
                                                    @AuthenticationPrincipal UserDetails details,
                                                    @PathVariable Long companyId) {

        companyService.update(request, companyId, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok(BaseResponse.success());
    }

    @GetMapping
    public ResponseEntity<BaseResponse<BaseResponse.PageResponse<CompanyListResponse>>> getCompanies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long industryId,
            @RequestParam(required = false) CompanyType companyType,
            Pageable pageable) {

        Page<CompanyListResponse> response = companyService.getCompanies(name, industryId, companyType, pageable);

        return ResponseEntity.ok(BaseResponse.success(
                BaseResponse.PageResponse.<CompanyListResponse>builder()
                        .content(response.getContent())
                        .pageInfo(BaseResponse.PageInfo.builder()
                                .currentPage(response.getNumber())
                                .totalPages(response.getTotalPages())
                                .totalElements(response.getTotalElements())
                                .hasNext(response.hasNext())
                                .build())
                        .build())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CompanyResponse>> getCompany(@PathVariable Long id) {

        return ResponseEntity.ok(BaseResponse.success(companyService.getCompany(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteCompany(@PathVariable Long id) {

        companyService.deleteCompany(id);

        return ResponseEntity.ok(BaseResponse.success());
    }
}
