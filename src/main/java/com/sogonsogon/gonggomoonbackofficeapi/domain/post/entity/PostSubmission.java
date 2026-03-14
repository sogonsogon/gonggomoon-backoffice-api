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
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post_submissions")
public class PostSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "platform_id", nullable = false)
    private Long platformId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostSubmissionStatus status;

    @Column(name = "processed_by")
    private Long processedBy;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "processed_at", nullable = false)
    private Instant processedAt;

    protected PostSubmission() {}

    public void approveSummit(Long processedBy) {
        this.status = PostSubmissionStatus.APPROVED;
        this.processedBy = processedBy;
    }

    public void rejectSummit(String rejectionReason, Long processedBy) {
        this.status = PostSubmissionStatus.REJECTED;
        this.processedBy = processedBy;
        this.rejectionReason = rejectionReason;
    }
}
