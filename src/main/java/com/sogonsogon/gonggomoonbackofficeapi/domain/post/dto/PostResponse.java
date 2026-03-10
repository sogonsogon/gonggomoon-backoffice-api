package com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.JobType;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostStatus;

import java.time.Instant;

public record PostResponse(
        Long companyId,
        String companyName,
        String industryName,
        String title,
        Integer experienceLevel,
        Instant deadline,
        String description,
        JobType type,
        PostStatus status
) {
}
