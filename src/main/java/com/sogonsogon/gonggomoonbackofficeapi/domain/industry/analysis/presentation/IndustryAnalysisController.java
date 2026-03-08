package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.application.IndustryAnalysisService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.request.CreateReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.request.UpdateReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.response.ReportResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.dto.response.ReportsResponse;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/industries/{id}/reports")
    public ResponseEntity<Map<String, List<ReportsResponse>>> getReports(@PathVariable Long id) {

        return ResponseEntity.ok(industryAnalysisService.getReports(id));
    }

    @GetMapping("/industries/reports/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable Long id) {

        return ResponseEntity.ok(industryAnalysisService.getReport(id));
    }

    @PatchMapping("/industries/reports/{id}/publish")
    public ResponseEntity<Void> publishReport(@PathVariable Long id) {

        industryAnalysisService.publishReport(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/industries/reports/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {

        industryAnalysisService.deleteReport(id);

        return ResponseEntity.ok().build();
    }
}
