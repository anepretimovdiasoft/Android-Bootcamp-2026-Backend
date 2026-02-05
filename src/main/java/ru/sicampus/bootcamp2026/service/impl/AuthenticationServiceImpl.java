package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.AuthResponse;
import ru.sicampus.bootcamp2026.dto.RegisterRequest;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.ResourceAlreadyExists;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new ResourceAlreadyExists("User with this email already exists");
        }

        String passwordHash = passwordEncoder.encode(registerRequest.getPassword());

        User user = User.builder()
                .email(registerRequest.getEmail())
                .position(registerRequest.getPosition())
                .department(registerRequest.getDepartment())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .passwordHash(passwordHash)
                .build();

        userRepository.save(user);

        return new AuthResponse();
    }
}
