package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity;

import com.sogonsogon.gonggomoonbackofficeapi.domain.industry.error.IndustryErrorCode;
import com.sogonsogon.gonggomoonbackofficeapi.global.error.BaseException;
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
@Table(name = "industries")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected Industry () {}

    @Builder
    private Industry(String name, Long createdBy) {
        this.name = name;
        this.createdBy = createdBy;
    }

    public static Industry create(String name, Long createdBy) {

        if (name == null || name.isBlank()) throw new BaseException(IndustryErrorCode.INVALID_INDUSTRY_NAME);
        if (createdBy == null || createdBy < 0) throw new BaseException(IndustryErrorCode.INVALID_INDUSTRY_CREATOR);

        return Industry.builder()
                .name(name)
                .createdBy(createdBy)
                .build();
    }

    public void update(String name, Long updatedBy) {

        if (name == null || name.isBlank()) throw new BaseException(IndustryErrorCode.INVALID_INDUSTRY_NAME);
        if (id == null || id < 0) throw new BaseException(IndustryErrorCode.INVALID_INDUSTRY_CREATOR);

        this.name = name;
        this.updatedBy = updatedBy;
    }
}
