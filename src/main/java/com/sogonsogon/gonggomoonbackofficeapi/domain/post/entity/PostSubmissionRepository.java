package com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.SubmissionListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostSubmissionRepository {

    Optional<PostSubmission> findById(Long submissionId);

    Page<SubmissionListResponse> findByStatus(
            PostSubmissionStatus status,
            Pageable pageable
    );
}
