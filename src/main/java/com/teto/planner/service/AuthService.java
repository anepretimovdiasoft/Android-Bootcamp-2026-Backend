package com.teto.planner.service;

import com.teto.planner.dto.UserMeDto;
import com.teto.planner.entity.UserEntity;
import com.teto.planner.exception.UnauthorizedException;
import com.teto.planner.mapper.UserMapper;
import com.teto.planner.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserMeDto login(String login, String password) {
        UserEntity user = userRepository.findByLoginIgnoreCase(login)
                .orElseThrow(() -> new UnauthorizedException("UNAUTHORIZED", "Invalid credentials"));
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new UnauthorizedException("UNAUTHORIZED", "Invalid credentials");
        }
        return userMapper.toMe(user);
    }
}
