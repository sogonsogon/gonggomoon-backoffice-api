package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.application.IndustryReportService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.request.CreateIndustryReportRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryReportResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.dto.response.IndustryReportListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.global.response.BaseResponse;
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
    public ResponseEntity<BaseResponse<Void>> createReport(@PathVariable Long industryId,
                                                     @RequestBody @Valid CreateIndustryReportRequest request,
                                                     @AuthenticationPrincipal UserDetails details) {

        industryReportService.createReport(request, industryId, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok(BaseResponse.success());
    }

    @GetMapping("/industries/{industryId}/reports")
    public ResponseEntity<BaseResponse<IndustryReportListResponse>> getReports(@PathVariable Long industryId) {

        return ResponseEntity.ok(BaseResponse.success(industryReportService.getReports(industryId)));
    }

    @GetMapping("/industries/reports/{id}")
    public ResponseEntity<BaseResponse<IndustryReportResponse>> getReport(@PathVariable Long id) {

        return ResponseEntity.ok(BaseResponse.success(industryReportService.getReport(id)));
    }

    @PatchMapping("/industries/reports/{id}/publish")
    public ResponseEntity<BaseResponse<Void>> publishReport(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetails details) {

        industryReportService.publishReport(id, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok(BaseResponse.success());
    }

    @DeleteMapping("/industries/reports/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteReport(@PathVariable Long id) {

        industryReportService.deleteReport(id);

        return ResponseEntity.ok(BaseResponse.success());
    }
}
