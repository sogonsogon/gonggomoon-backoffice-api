package com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostListResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.Post;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Long>, PostRepository {

    @Query(
            value = """
                SELECT new com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostListResponse(
                      p.id,
                      c.id,
                      p.platformId,
                      p.title,
                      c.name,
                      pl.name,
                      p.status,
                      p.startedAt,
                      p.expiredAt,
                      CASE WHEN p.expiredAt IS NOT NULL AND p.expiredAt < :now THEN true ELSE false END
                      )
                FROM Post p
                JOIN Company c ON c.id = p.companyId
                LEFT JOIN Platform pl ON pl.id = p.platformId
                WHERE (:title IS NULL OR p.title LIKE %:title%)
                AND (:statuses IS NULL OR p.status IN :statuses)
           """,
            countQuery = """
                SELECT COUNT(p)
                FROM Post p
                WHERE (:title IS NULL OR p.title LIKE %:title%)
                AND (:statuses IS NULL OR p.status IN :statuses)
        """)
    Page<PostListResponse> searchPostsByTitleAndStatus(
            @Param("title") String title,
            @Param("statuses") List<PostStatus> statuses,
            @Param("now") Instant now,
            Pageable pageable);

    @Query(
            value = """
                SELECT new com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostResponse(
                      p.id,
                      c.id,
                      i.id,
                      pl.id,
                      p.analyzedContent,
                      p.title,
                      c.name,
                      i.name,
                      pl.name,
                      p.url,
                      p.experienceLevel,
                      p.jobType,
                      p.originalContent,
                      p.status,
                      p.startedAt,
                      p.expiredAt
                      )
                    FROM Post p
                    LEFT JOIN Platform pl ON pl.id = p.platformId
                    JOIN Company c ON c.id = p.companyId
                    JOIN Industry i ON i.id = c.industryId
                    WHERE p.id = :id
           """)
    Optional<PostResponse> searchPostById(@Param("id") Long id);
}