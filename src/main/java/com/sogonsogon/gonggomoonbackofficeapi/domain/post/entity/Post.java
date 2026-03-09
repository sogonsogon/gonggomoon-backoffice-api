package com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "summit_id")
    private Long summitId;

    @Column(name = "company_id")
    private Long companyId;

    private String title;

    @Column(name = "experience_level")
    private Integer experienceLevel;

    @Column(name = "post_status")
    private PostStatus status;

    @Column(name = "job_type")
    private JobType jobType;

    private Instant deadline;

    @Column(name = "analyzed_at")
    private Instant analyzedAt;

    @Column(name = "posted_at")
    private Instant postedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected Post() {}

    @Builder
    private Post(Long summitId, Long companyId, String title, Integer experienceLevel, JobType jobType, Instant deadline) {
        this.summitId = summitId;
        this.companyId = companyId;
        this.title = title;
        this.experienceLevel = experienceLevel;
        this.jobType = jobType;
        this.deadline = deadline;
        this.status = PostStatus.ANALYZING;
    }

    public static Post create(Long summitId, Long companyId, String title,
                              Integer experienceLevel, JobType jobType, Instant deadline) {
        return Post.builder()
                .summitId(summitId)
                .companyId(companyId)
                .title(title)
                .experienceLevel(experienceLevel)
                .jobType(jobType)
                .deadline(deadline)
                .build();
    }

}
