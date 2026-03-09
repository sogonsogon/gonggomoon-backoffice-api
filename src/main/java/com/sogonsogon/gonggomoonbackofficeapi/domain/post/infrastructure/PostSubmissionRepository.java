package com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmission;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.SubmissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostSubmissionRepository extends JpaRepository<PostSubmission, Long> {

    Page<PostSubmission> findByStatus(SubmissionStatus status, Pageable pageable);
}
