package ru.sicampus.bootcamp2026.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    USER,
    MODERATOR;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
