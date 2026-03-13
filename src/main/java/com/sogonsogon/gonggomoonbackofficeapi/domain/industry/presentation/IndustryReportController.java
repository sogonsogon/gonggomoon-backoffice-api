package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.application.IndustryReportService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.request.CreateIndustryReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryReportResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryReportListResponse;
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
public class IndustryReportController {

    private final IndustryReportService industryReportService;

    public IndustryReportController(IndustryReportService industryReportService) {
        this.industryReportService = industryReportService;
    }

    /**
     * 분석 생성
     */
    @PostMapping("/industries/{industryId}/reports")
    public ResponseEntity<Void> createReport(@PathVariable Long industryId,
                                             @RequestBody @Valid CreateIndustryReportRequest request,
                                             @AuthenticationPrincipal UserDetails details) {

        industryReportService.createReport(request, industryId, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/industries/{industryId}/reports")
    public ResponseEntity<IndustryReportListResponse> getReports(@PathVariable Long industryId) {

        return ResponseEntity.ok(industryReportService.getReports(industryId));
    }

    @GetMapping("/industries/reports/{id}")
    public ResponseEntity<IndustryReportResponse> getReport(@PathVariable Long id) {

        return ResponseEntity.ok(industryReportService.getReport(id));
    }

    @PatchMapping("/industries/reports/{id}/publish")
    public ResponseEntity<Void> publishReport(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetails details) {

        industryReportService.publishReport(id, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/industries/reports/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {

        industryReportService.deleteReport(id);

        return ResponseEntity.ok().build();
    }
}
