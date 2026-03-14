package com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmissionStatus;

import java.time.Instant;

public record SubmissionListResponse(
        Long submissionId,
        Long summitedBy,
        Long platformId,
        String platformName,
        String url,
        PostSubmissionStatus submissionStatus,
        Instant createdAt
) {
}
