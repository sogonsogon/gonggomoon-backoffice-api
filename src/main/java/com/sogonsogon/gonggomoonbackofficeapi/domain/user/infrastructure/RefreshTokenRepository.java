package com.sogonsogon.gonggomoonbackofficeapi.domain.user.infrastructure;

import com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByUserId(Long userId);
}
