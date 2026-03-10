package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.category.entity;

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
@Table(name = "industry_categories")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class IndustryCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "published_report_id")
    private Long publishedReportId;

    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected IndustryCategory() {}

    @Builder
    private IndustryCategory(String categoryName, Long createdBy) {
        this.categoryName = categoryName;
        this.createdBy = createdBy;
    }

    public static IndustryCategory create(String categoryName, Long createdBy) {
        //TODO: private으로 뺄거임
        if (categoryName == null || categoryName.isBlank()) throw new IllegalArgumentException();
        if (createdBy == null || createdBy <= 0) throw new IllegalArgumentException();

        return IndustryCategory.builder()
                .categoryName(categoryName)
                .createdBy(createdBy)
                .build();
    }

    public void update(String categoryName, Long id) {

        if (categoryName == null || categoryName.isBlank()) throw new IllegalArgumentException();
        if (id == null || id <= 0) throw new IllegalArgumentException();

        this.categoryName = categoryName;
        this.updatedBy = id;
    }

    public void updatePublishedReport(Long id) {
        this.publishedReportId = id;
    }
}
