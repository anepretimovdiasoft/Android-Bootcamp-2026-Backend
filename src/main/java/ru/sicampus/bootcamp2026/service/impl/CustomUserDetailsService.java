package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ru.sicampus.bootcamp2026.entity.User appUser = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(appUser.getEmail())
                .password(appUser.getPasswordHash())
                .authorities(Collections.emptyList())
                .build();
    }
}
