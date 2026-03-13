package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity;

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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Table(name = "industry_reports")
@EntityListeners(AuditingEntityListener.class)
public class IndustryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "industry_id", nullable = false)
    private Long industryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private IndustryReportStatus status;

    @Column(name = "report_year")
    private Integer reportYear;

    @Column(name = "competition", columnDefinition = "TEXT")
    private String competition;

    @Column(name = "market_size", columnDefinition = "TEXT")
    private String marketSize;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "trend", columnDefinition = "jsonb")
    private List<String> trend;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regulation", columnDefinition = "jsonb")
    private List<String> regulation;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "keyword", columnDefinition = "jsonb")
    private List<String> keyword;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "hiring", columnDefinition = "jsonb")
    private List<String> hiring;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "investment", columnDefinition = "jsonb")
    private List<String> investment;

    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by")
    private Long publishedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "published_at")
    private Instant publishedAt;

    protected IndustryReport() {}

    @Builder
    private IndustryReport(Long industryId, Integer reportYear, String competition, String marketSize,
                           List<String> trend, List<String> regulation, List<String> keyword,
                           List<String> hiring, List<String> investment, Long createdBy) {
        this.industryId = industryId;
        this.status = IndustryReportStatus.PENDING;
        this.reportYear = reportYear;
        this.competition = competition;
        this.marketSize = marketSize;
        this.trend = trend;
        this.regulation = regulation;
        this.keyword = keyword;
        this.hiring = hiring;
        this.investment = investment;
        this.createdBy = createdBy;
    }

    /**
     * 검증 로직 확인 필요
     */
    public static IndustryReport create(Long industryId, Integer reportYear, String competition, String marketSize,
                                        List<String> trend, List<String> regulation, List<String> keyword,
                                        List<String> hiring, List<String> investment, Long createdBy) {
        return IndustryReport.builder()
                .industryId(industryId)
                .reportYear(reportYear)
                .competition(competition)
                .marketSize(marketSize)
                .trend(trend)
                .regulation(regulation)
                .keyword(keyword)
                .hiring(hiring)
                .investment(investment)
                .createdBy(createdBy)
                .build();
    }

    public void publish(Long publishedBy) {
        this.status = IndustryReportStatus.PUBLISHED;
        this.publishedBy = publishedBy;
        this.publishedAt = Instant.now();
    }
}
