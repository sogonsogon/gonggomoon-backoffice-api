package com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum UserRole {
    ADMIN("ROLE_USER"),
    USER("ROLE_ADMIN");

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }
}
