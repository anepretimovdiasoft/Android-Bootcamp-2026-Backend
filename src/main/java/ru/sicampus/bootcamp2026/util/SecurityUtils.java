package ru.sicampus.bootcamp2026.util;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.sicampus.bootcamp2026.entity.User;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static User getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("user not found but it should be");
        }

        return (User) auth.getPrincipal();
    }
}
