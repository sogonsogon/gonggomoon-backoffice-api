package com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmission;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.SubmissionPlatform;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.SubmissionStatus;

import java.time.Instant;

public record SubmitPostResponse(
        Long id,
        SubmissionStatus status,
        SubmissionPlatform platform,
        String url,
        Long summitedBy,
        Instant createdAt

) {
    public static SubmitPostResponse from(PostSubmission submission) {
        return new SubmitPostResponse(
                submission.getId(),
                submission.getStatus(),
                submission.getPlatform(),
                submission.getRequestUrl(),
                submission.getRequestedBy(),
                submission.getCreatedAt()
        );
    }
}
