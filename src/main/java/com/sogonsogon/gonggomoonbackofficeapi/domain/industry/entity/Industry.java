package com.sogonsogon.gonggomoonbackofficeapi.domain.industry.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "industries")
@Getter
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE industries SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
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

    @Column(name = "is_deleted")
    private boolean isDeleted;

    protected Industry () {}

    @Builder
    private Industry(String name, Long createdBy) {
        this.name = name;
        this.createdBy = createdBy;
        this.isDeleted = false;
    }

    public static Industry create(String name, Long createdBy) {

        if (name == null || name.isBlank()) throw new IllegalArgumentException();
        if (createdBy == null || createdBy <= 0) throw new IllegalArgumentException();

        return Industry.builder()
                .name(name)
                .createdBy(createdBy)
                .build();
    }

    public void update(String name, Long updatedBy) {

        if (name == null || name.isBlank()) throw new IllegalArgumentException();
        if (id == null || id <= 0) throw new IllegalArgumentException();

        this.name = name;
        this.updatedBy = updatedBy;
    }
}
