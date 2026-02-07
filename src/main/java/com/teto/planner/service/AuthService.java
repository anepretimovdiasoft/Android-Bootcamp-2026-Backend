package com.teto.planner.service;

import com.teto.planner.dto.UserMeDto;
import com.teto.planner.entity.UserEntity;
import com.teto.planner.exception.ConflictException;
import com.teto.planner.exception.UnauthorizedException;
import com.teto.planner.mapper.UserMapper;
import com.teto.planner.repository.UserRepository;
import java.util.UUID;
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

    public UserMeDto register(String login, String name, String password, String telegramNick, String bio) {
        if (userRepository.findByLoginIgnoreCase(login).isPresent()) {
            throw new ConflictException("LOGIN_EXISTS", "Login already exists");
        }
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setLogin(login);
        user.setName(name);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setTelegramNick(telegramNick);
        user.setBio(bio);
        userRepository.save(user);
        return userMapper.toMe(user);
    }
}
