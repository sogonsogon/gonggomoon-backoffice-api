package com.sogonsogon.gonggomoonbackofficeapi.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public static UserRole fromAuthority(String authority) {
        for (UserRole role : UserRole.values()) {
            if (role.getAuthority().equals(authority)) return role;
        } throw new IllegalArgumentException();
    }
}
