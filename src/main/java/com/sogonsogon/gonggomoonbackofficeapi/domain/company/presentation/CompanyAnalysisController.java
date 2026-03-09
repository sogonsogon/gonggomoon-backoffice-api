package com.sogonsogon.gonggomoonbackofficeapi.domain.company.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.application.CompanyAnalysisService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyReportResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CompanyReportsResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CreateCompanyReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.UpdateCompanyReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity.CompanyType;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.entity.IndustryType;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
public class CompanyAnalysisController {

    private final CompanyAnalysisService companyAnalysisService;

    public CompanyAnalysisController(CompanyAnalysisService companyAnalysisService) {
        this.companyAnalysisService = companyAnalysisService;
    }

    @PostMapping
    public ResponseEntity<Void> createCompanyReport(@RequestBody @Valid CreateCompanyReportRequest request,
                                                    @AuthenticationPrincipal UserDetails details) {

        companyAnalysisService.create(request, Long.valueOf(details.getUsername()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCompanyReport(@RequestBody @Valid UpdateCompanyReportRequest request,
                                                    @AuthenticationPrincipal UserDetails details,
                                                    @PathVariable Long id) {

        companyAnalysisService.update(request, id, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<CompanyReportsResponse>> getCompanyReports(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) IndustryType industryType,
            @RequestParam(required = false) CompanyType companyType,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(companyAnalysisService.getCompanyReports(name, industryType, companyType, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyReportResponse> getCompanyReport(@PathVariable Long id) {

        return ResponseEntity.ok(companyAnalysisService.getCompanyReport(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyReport(@PathVariable Long id) {

        companyAnalysisService.deleteCompanyReport(id);

        return ResponseEntity.ok().build();
    }
}
