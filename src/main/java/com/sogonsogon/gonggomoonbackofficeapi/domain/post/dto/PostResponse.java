package com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.JobType;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.Post;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostStatus;

import java.time.Instant;

public record PostResponse(
        Long postId,
        Long companyId,
        Long industryId,
        Long platformId,
        JsonNode analyzedContent,
        String postTitle,
        String companyName,
        String industryName,
        String platformName,
        String postUrl,
        Integer experienceLevel,
        JobType jobType,
        String originalContent,
        PostStatus status,
        Instant startDate,
        Instant dueDate
) {
}
