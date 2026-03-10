package com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.JobType;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostStatus;

import java.time.Instant;

// AI 한줄 분석 추가해야 함
public record PostsResponse(
        Long id,
        String title,
        Integer experienceLevel,
        String companyName,
        JobType jobType,
        Instant deadline,
        PostStatus status
) {
}
