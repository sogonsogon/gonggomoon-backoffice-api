package com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(
    name = "local_credential",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_local_credential_user_id", columnNames = "user_id")
    },
    indexes = {
        @Index(name = "idx_local_credential_user_id", columnList = "user_id")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * users 테이블 PK 참조
     * 연관관계 매핑 없이 ID 값만 관리
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 단방향 암호화된 비밀번호 해시값
     */
    @Column(name = "password", nullable = false, length = 255)
    private String passwordHash;

    /**
     * 비밀번호 변경 시각 (UTC)
     */
    @Column(name = "updated_at")
    private Instant passwordChangedAt;

    @Builder
    private LocalCredential(Long userId, String passwordHash, Instant passwordChangedAt) {
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.passwordChangedAt = passwordChangedAt;
    }

    public static LocalCredential create(Long userId, String passwordHash) {
        return LocalCredential.builder()
            .userId(userId)
            .passwordHash(passwordHash)
            .passwordChangedAt(Instant.now())
            .build();
    }

    public void changePassword(String passwordHash) {
        this.passwordHash = passwordHash;
        this.passwordChangedAt = Instant.now();
    }
}