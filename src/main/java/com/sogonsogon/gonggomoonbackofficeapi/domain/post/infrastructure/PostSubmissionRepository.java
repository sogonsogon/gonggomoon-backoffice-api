package com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostSubmissionRepository extends JpaRepository<PostSubmission, Long> {
}
