package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.application.IndustryAnalysisService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.request.CreateReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.request.UpdateReportRequest;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/admin")
public class IndustryAnalysisController {

    private final IndustryAnalysisService industryAnalysisService;

    public IndustryAnalysisController(IndustryAnalysisService industryAnalysisService) {
        this.industryAnalysisService = industryAnalysisService;
    }

    /**
     * 분석 생성
     */
    @PostMapping("/industries/{industryId}/reports")
    public ResponseEntity<Void> createReport(@PathVariable Long industryId,
                                             @RequestBody @Valid CreateReportRequest request,
                                             @AuthenticationPrincipal UserDetails details) {

        industryAnalysisService.createReport(request, industryId, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/industries/reports/{reportId}")
    public ResponseEntity<Void> updateReport(@PathVariable Long reportId,
                                             @RequestBody @Valid UpdateReportRequest request,
                                             @AuthenticationPrincipal UserDetails details) {

        industryAnalysisService.updateReport(request, reportId, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok().build();
    }
}
