package com.sogonsogon.gonggomoonbackofficeapi.domain.company.entity;

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
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Table(name = "company_analyses")
@EntityListeners(AuditingEntityListener.class)
public class CompanyAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "industry_category_id", nullable = false)
    private Long industryCategoryId;

    @Column(name = "company_name")
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type")
    private CompanyType companyType;

    @Column(name = "employee_count")
    private Integer employeeCount;

    private String address;

    private Long revenue;

    @Column(name = "founded_year")
    private Integer foundedYear;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected CompanyAnalysis() {}

    @Builder
    private CompanyAnalysis(Long industryCategoryId, String companyName, CompanyType companyType, Integer employeeCount,
                           String address, Long revenue, Integer foundedYear, String websiteUrl,
                           String description, Long createdBy) {
        this.industryCategoryId = industryCategoryId;
        this.companyName = companyName;
        this.companyType = companyType;
        this.employeeCount = employeeCount;
        this.address = address;
        this.revenue = revenue;
        this.foundedYear = foundedYear;
        this.websiteUrl = websiteUrl;
        this.description = description;
        this.createdBy = createdBy;
    }

    public static CompanyAnalysis create(Long industryCategoryId, String companyName, CompanyType companyType, Integer employeeCount,
                                         String address, Long revenue, Integer foundedYear, String websiteUrl,
                                         String description, Long createdBy) {
        return CompanyAnalysis.builder()
                .industryCategoryId(industryCategoryId)
                .companyName(companyName)
                .companyType(companyType)
                .employeeCount(employeeCount)
                .address(address)
                .revenue(revenue)
                .foundedYear(foundedYear)
                .websiteUrl(websiteUrl)
                .description(description)
                .createdBy(createdBy)
                .build();
    }

    public void update(Long industryCategoryId, String companyName, CompanyType companyType, Integer employeeCount,
                       String address, Long revenue, Integer foundedYear, String websiteUrl,
                       String description, Long createdBy) {

        Optional.ofNullable(industryCategoryId).ifPresent(v -> this.industryCategoryId = v);
        Optional.ofNullable(companyName).ifPresent(v -> this.companyName = v);
        if (companyType != null) this.companyType = companyType;
        Optional.ofNullable(employeeCount).ifPresent(v -> this.employeeCount = v);
        Optional.ofNullable(address).ifPresent(v -> this.address = v);
        Optional.ofNullable(revenue).ifPresent(v -> this.revenue = v);
        Optional.ofNullable(foundedYear).ifPresent(v -> this.foundedYear = v);
        Optional.ofNullable(websiteUrl).ifPresent(v -> this.websiteUrl = v);
        Optional.ofNullable(description).ifPresent(v -> this.description = v);
        Optional.ofNullable(createdBy).ifPresent(v -> this.createdBy = v);
    }
}
