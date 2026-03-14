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
import java.util.Optional;

@Entity
@Getter
@Table(name = "companies")
@EntityListeners(AuditingEntityListener.class)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "industry_id", nullable = false)
    private Long industryId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanyType type;

    @Column(name = "employee_count")
    private Integer employeeCount;

    @Column(name = "address")
    private String address;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "founded_year")
    private Integer foundedYear;

    @Column(name = "url")
    private String url;

    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Company() {}

    @Builder
    private Company(Long industryId, String name, CompanyType type,
                   Integer employeeCount, String address, String description,
                   Integer foundedYear, String url, Long createdBy) {
        this.industryId = industryId;
        this.name = name;
        this.type = type;
        this.employeeCount = employeeCount;
        this.address = address;
        this.description = description;
        this.foundedYear = foundedYear;
        this.url = url;
        this.createdBy = createdBy;
    }

    public static Company create(Long industryId, String name, CompanyType type,
                                 Integer employeeCount, String address, String description,
                                 Integer foundedYear, String url, Long createdBy) {
        return Company.builder()
                .industryId(industryId)
                .name(name)
                .type(type)
                .employeeCount(employeeCount)
                .address(address)
                .foundedYear(foundedYear)
                .url(url)
                .description(description)
                .createdBy(createdBy)
                .build();
    }

    public void update(Long industryId, String name, CompanyType type,
                       Integer employeeCount, String address, String description,
                       Integer foundedYear, String url, Long createdBy) {

        Optional.ofNullable(industryId).ifPresent(v -> this.industryId = v);
        Optional.ofNullable(name).ifPresent(v -> this.name = v);
        Optional.ofNullable(type).ifPresent(v -> this.type = v);
        Optional.ofNullable(employeeCount).ifPresent(v -> this.employeeCount = v);
        Optional.ofNullable(address).ifPresent(v -> this.address = v);
        Optional.ofNullable(description).ifPresent(v -> this.description = v);
        Optional.ofNullable(foundedYear).ifPresent(v -> this.foundedYear = v);
        Optional.ofNullable(url).ifPresent(v -> this.url = v);
        Optional.ofNullable(createdBy).ifPresent(v -> this.createdBy = v);
    }
}
