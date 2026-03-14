package com.sogonsogon.gonggomoonbackofficeapi.domain.post.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.application.PostSubmissionService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.CreatePostRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.RejectSummitRequest;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.SubmissionListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmissionStatus;
import com.sogonsogon.gonggomoonbackofficeapi.global.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/submissions")
public class PostSubmissionController {

    private final PostSubmissionService submissionService;

    public PostSubmissionController(PostSubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PutMapping("/{submissionId}/approve")
    public ResponseEntity<BaseResponse<Void>> approveSubmission(@PathVariable Long submissionId,
                                                                @RequestBody @Valid CreatePostRequest request,
                                                                @AuthenticationPrincipal UserDetails details
    ) {

        submissionService.approveSubmission(submissionId, request, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok(BaseResponse.success());
    }

    @PutMapping("/{id}/rejcet")
    public ResponseEntity<BaseResponse<Void>> rejectSubmission(@PathVariable Long id,
                                                 @RequestBody @Valid RejectSummitRequest request,
                                                 @AuthenticationPrincipal UserDetails details) {

        submissionService.rejectSubmission(id, request, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok(BaseResponse.success());
    }

    @GetMapping
    public ResponseEntity<BaseResponse<BaseResponse.PageResponse<SubmissionListResponse>>> getSubmissions(
            @RequestParam PostSubmissionStatus status,
            Pageable pageable) {

        Page<SubmissionListResponse> response = submissionService.getSubmissions(status, pageable);

        return ResponseEntity.ok(BaseResponse.success(
                BaseResponse.PageResponse.<SubmissionListResponse>builder()
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
}
