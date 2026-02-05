package com.teto.planner.service;

import com.teto.planner.entity.UserEntity;
import com.teto.planner.exception.NotFoundException;
import com.teto.planner.exception.UnauthorizedException;
import com.teto.planner.repository.UserRepository;
import com.teto.planner.security.UserPrincipal;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("UNAUTHORIZED", "Missing credentials");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserPrincipal userPrincipal) {
            UUID userId = userPrincipal.getId();
            return userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User not found"));
        }
        if (principal instanceof UserDetails userDetails) {
            return userRepository.findByLoginIgnoreCase(userDetails.getUsername())
                    .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User not found"));
        }

        throw new UnauthorizedException("UNAUTHORIZED", "Missing credentials");
    }
}
