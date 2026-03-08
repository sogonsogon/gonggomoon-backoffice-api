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

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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

    //TODO: 만약 유저가 내용을 전부 삭제한 필드는 [] 빈값이 들어와야 함 FE에게 공유 필요함
    public void update(Long industryCategoryId, Integer analysis_year, Long userId,
                       String competition, String marketSize, List<String> trend, List<String> regulation,
                       List<String> keyword, List<String> hiring, List<String> investment) {

        Optional.ofNullable(industryCategoryId).ifPresent(v -> this.industryCategoryId = v);
        Optional.ofNullable(analysis_year).ifPresent(v -> this.analysis_year = v);
        Optional.ofNullable(userId).ifPresent(v -> this.updatedBy = v);
        Optional.ofNullable(competition).ifPresent(v -> this.competition = v);
        Optional.ofNullable(marketSize).ifPresent(v -> this.marketSize = v);
        Optional.ofNullable(trend).ifPresent(v -> this.trend = v);
        Optional.ofNullable(regulation).ifPresent(v -> this.regulation = v);
        Optional.ofNullable(keyword).ifPresent(v -> this.keyword = v);
        Optional.ofNullable(hiring).ifPresent(v -> this.hiring = v);
        Optional.ofNullable(investment).ifPresent(v -> this.investment = v);
    }

    /**
     * 현재 상태 값 확인
     */
    public void publish() {
        this.status = IndustryAnalysisStatus.PUBLISHED;
    }
}
