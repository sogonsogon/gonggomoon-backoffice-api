package com.sogonsogon.gonggomoonbackofficeapi.domain.company.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.company.application.CompanyAnalysisService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.CreateCompanyReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.company.dto.UpdateCompanyReportRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
