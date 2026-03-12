package com.sogonsogon.gonggomoonbackofficeapi.global.security.principal;

import com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity.User;
import com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;
    private final String passwordHash;

    public CustomUserDetails(User user, String passwordHash) {
        this.user = user;
        this.passwordHash = passwordHash;
    }

    public static CustomUserDetails of(User user, String passwordHash) {
        return new CustomUserDetails(user, passwordHash);
    }

    public static CustomUserDetails ofToken(Long id, Collection<? extends GrantedAuthority> authorities) {
        // 토큰에 담긴 권한 문자열(ROLE_ADMIN 등)을 다시 UserRole Enum으로 역변환
        String authority = authorities.iterator().next().getAuthority();
        UserRole role = UserRole.fromAuthority(authority);

        // 빌더가 생성자에 붙어 있으므로, 필요한 필드만 채워서 가짜 유저를 만듭니다.
        User tempUser = User.createAuthorityUser(id, role);

        // passwordHash는 토큰에서 검증할 수 없으므로 null로 설정합니다. 실제 인증 과정에서는 이 메서드를 사용하지 않고, DB에서 조회한 UserDetails를 사용해야 합니다.
        return new CustomUserDetails(tempUser, null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(
                new SimpleGrantedAuthority(user.getRole().getAuthority())
        );
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return String.valueOf(user.getId());
    }
}
