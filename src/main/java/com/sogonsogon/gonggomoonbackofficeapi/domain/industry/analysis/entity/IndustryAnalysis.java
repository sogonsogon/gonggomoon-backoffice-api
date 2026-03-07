package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.analysis.entity;

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
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.SQLType;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Table(name = "industry_analyses")
@EntityListeners(AuditingEntityListener.class)
public class IndustryAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "industry_category_id", nullable = false)
    private Long industryCategoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "analysis_status", nullable = false)
    private IndustryAnalysisStatus status;

    @Column(name = "analysis_year")
    private Integer analysis_year;

    @Column(columnDefinition = "TEXT")
    private String competition;

    @Column(columnDefinition = "TEXT")
    private String marketSize;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> trend;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> regulation;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> keyword;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> hiring;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> investment;

    private Long createdBy;

    private Long updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected IndustryAnalysis() {}

    @Builder
    private IndustryAnalysis(Long industryCategoryId, IndustryAnalysisStatus status, Integer analysis_year,
                            String competition, String marketSize, List<String> trend, List<String> regulation,
                             List<String> keyword, List<String> hiring, List<String> investment, Long userId) {
        this.industryCategoryId = industryCategoryId;
        this.status = status;
        this.analysis_year = analysis_year;
        this.competition = competition;
        this.marketSize = marketSize;
        this.trend = trend;
        this.regulation = regulation;
        this.keyword = keyword;
        this.hiring = hiring;
        this.investment = investment;
        this.createdBy = userId;
    }

    /**
     * 검증 로직 확인 필요
     */
    public static IndustryAnalysis create(Long industryCategoryId, Integer analysis_year, Long userId,
                                          String competition, String marketSize, List<String> trend, List<String> regulation,
                                          List<String> keyword, List<String> hiring, List<String> investment) {
        return IndustryAnalysis.builder()
                .industryCategoryId(industryCategoryId)
                .status(IndustryAnalysisStatus.PENDING)
                .analysis_year(analysis_year)
                .userId(userId)
                .competition(competition)
                .marketSize(marketSize)
                .trend(trend)
                .regulation(regulation)
                .keyword(keyword)
                .hiring(hiring)
                .investment(investment)
                .build();
    }
}
