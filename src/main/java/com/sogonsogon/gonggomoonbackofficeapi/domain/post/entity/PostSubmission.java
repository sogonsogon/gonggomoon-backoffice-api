package com.sogonsogon.gonggomoonbackofficeapi.domain.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post_submissions")
public class PostSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requested_by", updatable = false)
    private Long requestedBy;

    @Column(name = "request_url")
    private String requestUrl;

    private SubmissionStatus status;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "approved_by", updatable = false)
    private Long approvedBy;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updated_at;

    protected PostSubmission() {}

    @Builder
    private PostSubmission(Long requestedBy, String requestUrl, Long approvedBy) {
        this.requestedBy = requestedBy;
        this.requestUrl = requestUrl;
        this.approvedBy = approvedBy;
    }

    public static PostSubmission create(Long requestedBy, String requestUrl, Long approvedBy) {
        return PostSubmission.builder()
                .requestedBy(requestedBy)
                .requestUrl(requestUrl)
                .approvedBy(approvedBy)
                .build();
    }


}
