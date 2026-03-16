package com.sogonsogon.gonggomoonbackofficeapi.domain.user.infrastructure;


import com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity.LocalCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalCredentialRepository extends JpaRepository<LocalCredential, Integer> {

    Optional<LocalCredential> findByUserId(Long userId);

}
