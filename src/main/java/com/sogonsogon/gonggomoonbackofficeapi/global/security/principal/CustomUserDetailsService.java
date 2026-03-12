package com.sogonsogon.gonggomoonbackofficeapi.global.security.principal;

import com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity.LocalCredential;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity.User;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.infrastructure.LocalCredentialRepository;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.infrastructure.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LocalCredentialRepository localCredentialRepository;

    public CustomUserDetailsService(UserRepository userRepository, LocalCredentialRepository localCredentialRepository) {
        this.userRepository = userRepository;
        this.localCredentialRepository = localCredentialRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. email=" + email));

        LocalCredential localCredential = localCredentialRepository.findByUserId(user.getId())
            .orElseThrow(() -> new UsernameNotFoundException("로컬 로그인 정보를 찾을 수 없습니다. userId=" + user.getId()));

        return CustomUserDetails.of(user, localCredential.getPasswordHash());
    }
}
