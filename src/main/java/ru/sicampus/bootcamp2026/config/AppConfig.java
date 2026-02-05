package ru.sicampus.bootcamp2026.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepository repository;

    @Bean
    UserDetailsService userDetailsService() {

        return username -> {
            return repository.findByEmail(username).orElseThrow(()
                    -> new ResourceNotFoundException("Username not found"));
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
