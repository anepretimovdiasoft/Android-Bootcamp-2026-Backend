package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.RegisterDto;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.exception.UserAlreadyExistsException;
import ru.sicampus.bootcamp2026.model.entity.Users;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.AuthService;
import ru.sicampus.bootcamp2026.util.UserMapper;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(RegisterDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        Users user = new Users();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPosition(dto.getPosition());
        return UserMapper.toDto(userRepository.save(user));
    }
}
