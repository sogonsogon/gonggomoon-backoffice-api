package com.sogonsogon.gonggomoonbackofficeapi.domain.post.presentation;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.application.PostSubmissionService;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.SubmitPostRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/submissions")
public class PostSubmissionController {

    private final PostSubmissionService submissionService;

    public PostSubmissionController(PostSubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    public ResponseEntity<Void> submitPost(@RequestBody @Valid SubmitPostRequest request,
                                           @AuthenticationPrincipal UserDetails details) {

        submissionService.submitPost(request, Long.valueOf(details.getUsername()));

        return ResponseEntity.ok().build();
    }
}
