package com.sogonsogon.gonggomoonbackofficeapi.domain.post.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostsResponse;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.JobType;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.Post;
import com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
           value = """
           SELECT new com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostsResponse(
                      p.id,
                      p.title,
                      p.experienceLevel,
                      c.companyName,
                      p.jobType,
                      p.deadline,
                      p.status
                      )
           FROM Post p
           Join CompanyAnalysis c ON c.id = p.companyId
           WHERE p.jobType = :type
           AND p.status = :status
           """,
    countQuery = """
        SELECT COUNT(p)
        FROM Post p
        JOIN CompanyAnalysis c ON c.id = p.companyId
        WHERE p.jobType = :type
        AND p.status = :status
        """)
    Page<PostsResponse> searchByJobType(@Param("type") JobType type,
                                        @Param("status") PostStatus status,
                                        Pageable pageable);

    @Query(
            value = """
           SELECT new com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostsResponse(
                      p.id,
                      p.title,
                      p.experienceLevel,
                      c.companyName,
                      p.jobType,
                      p.deadline,
                      p.status
                      )
           FROM Post p
           Join CompanyAnalysis c ON c.id = p.companyId
           WHERE p.title LIKE %:title%
           AND p.status = :status
           """,
            countQuery = """
        SELECT COUNT(p)
        FROM Post p
        JOIN CompanyAnalysis c ON c.id = p.companyId
        WHERE p.title LIKE %:title%
        AND p.status = :status
        """)
    Page<PostsResponse> searchByTitle(@Param("title") String title,
                             @Param("status") PostStatus status,
                             Pageable pageable);

    @Query(
            value = """
           SELECT new com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostsResponse(
                      p.id,
                      p.title,
                      p.experienceLevel,
                      c.companyName,
                      p.jobType,
                      p.deadline,
                      p.status
                      )
           FROM Post p
           Join CompanyAnalysis c ON c.id = p.companyId
           WHERE p.status = :status
           """,
            countQuery = """
        SELECT COUNT(p)
        FROM Post p
        JOIN CompanyAnalysis c ON c.id = p.companyId
        WHERE p.status = :status
        """)
    Page<PostsResponse> searchByStatus(@Param("status") PostStatus status, Pageable pageable);

    @Query(
            value = """
           SELECT new com.sogonsogon.gonggomoonbackofficeapi.domain.post.dto.PostResponse(
                      c.id,
                      c.companyName,
                      i.categoryName,
                      p.title,
                      p.experienceLevel,
                      p.deadline,
                      p.content,
                      p.jobType,
                      p.status
                      )
           FROM Post p
           Join CompanyAnalysis c ON c.id = p.companyId
           JOIN IndustryCategory i ON i.id = c.industryCategoryId
           WHERE p.id = :id
           AND p.status = :status
           """)
    Optional<PostResponse> searchById(@Param("id") Long id, @Param("status") PostStatus status);


}
