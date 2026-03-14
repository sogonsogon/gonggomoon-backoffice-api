package com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostStatus;

import java.time.Instant;

// AI 한줄 분석 추가해야 함
public record PostListResponse(
        Long postId,
        Long companyId,
        Long platformId,
        String postTitle,
        String companyName,
        String platformName,
        PostStatus postStatus,
        Instant startDate,
        Instant dueDate,
        boolean expired
) {
}
