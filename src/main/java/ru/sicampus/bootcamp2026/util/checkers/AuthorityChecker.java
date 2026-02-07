package ru.sicampus.bootcamp2026.util.checkers;

import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.exception.AuthorityNotFoundException;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;

import java.util.Optional;

public record AuthorityChecker() {
    public static Authority checkAuthority(AuthorityRepository authorityRepository, String role) {
        Optional<Authority> roleUser = authorityRepository.findByAuthority(role);
        if (roleUser.isEmpty()) {
            throw new AuthorityNotFoundException();
        }
        return roleUser.get();
    }
}
