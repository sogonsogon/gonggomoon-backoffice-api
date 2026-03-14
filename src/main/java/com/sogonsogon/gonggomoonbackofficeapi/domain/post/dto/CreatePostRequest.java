package com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.JobType;

import java.time.Instant;

public record CreatePostRequest(
        Long companyId,
        Long platformId,
        String title,
        String url,
        JobType jobType,
        String originalContent,
        Integer experienceLevel,
        Instant startDate,
        Instant dueDate
) {
}
