package com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.SubmissionListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmission;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmissionRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostSubmissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostSubmissionJpaRepository extends JpaRepository<PostSubmission, Long>, PostSubmissionRepository {

    @Query(value = """
        SELECT new com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.SubmissionListResponse(
            ps.id,
            ps.userId,
            ps.platformId,
            p.name,
            ps.url,
            ps.status,
            ps.createdAt
        )
        FROM PostSubmission ps
        JOIN Platform p ON p.id = ps.platformId
        WHERE (:status IS NULL OR ps.status = :status)
""",
    countQuery = """
        SELECT COUNT(ps)
        FROM PostSubmission ps
        WHERE (:status IS NULL OR ps.status = :status)
""")
    Page<SubmissionListResponse> findByStatus(
            @Param("status") PostSubmissionStatus status,
            Pageable pageable
    );

}
