package com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.JobType;

import java.time.Instant;

public record ApproveSummitRequest(
        Long summitId,
        Long companyId,
        String title,
        Integer experienceLevel,
        JobType jobType,
        Instant deadline
) {
}
