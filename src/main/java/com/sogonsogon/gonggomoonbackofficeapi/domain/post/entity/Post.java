package com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

//TODO createdBy, publishedBy 있어야 할 듯?
@Entity
@Getter
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "submission_id")
    private Long submissionId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "platform_id")
    private Long platformId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url")
    private String url;

    @Column(name = "experience_level")
    private Integer experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false)
    private JobType jobType;

    @Column(name = "original_content", nullable = false, columnDefinition = "TEXT")
    private String originalContent;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "expired_at")
    private Instant expiredAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "analyzed_at")
    private Instant analyzedAt;

    @Column(name = "published_at")
    private Instant publishedAt;

    protected Post() {}

    @Builder
    private Post(Long submissionId, Long companyId, Long platformId, String title,
                String url, Integer experienceLevel, JobType jobType,
                String originalContent, Instant startedAt, Instant expiredAt) {
        this.submissionId = submissionId;
        this.companyId = companyId;
        this.platformId = platformId;
        this.title = title;
        this.url = url;
        this.experienceLevel = experienceLevel;
        this.jobType = jobType;
        this.originalContent = originalContent;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
    }

    public static Post create(Long submissionId, Long companyId, Long platformId, String title,
                              String url, Integer experienceLevel, JobType jobType,
                              String originalContent, Instant startedAt, Instant expiredAt) {
        return Post.builder()
                .submissionId(submissionId)
                .companyId(companyId)
                .platformId(platformId)
                .title(title)
                .url(url)
                .experienceLevel(experienceLevel)
                .jobType(jobType)
                .originalContent(originalContent)
                .startedAt(startedAt)
                .expiredAt(expiredAt)
                .build();
    }

    public void publish() {
        this.status = PostStatus.PUBLISHED;
        this.publishedAt = Instant.now();
    }

}
