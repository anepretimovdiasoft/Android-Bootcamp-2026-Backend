package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.aspect.annotation.LogExample;
import ru.sicampus.bootcamp2026.entity.Role;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exceptions.ResourceConflictException;
import ru.sicampus.bootcamp2026.exceptions.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.RoleRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.AuthService;
import ru.sicampus.bootcamp2026.web.dto.user.UserRegisterDto;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @LogExample
    @Transactional(readOnly = true)
    public User login(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден."));
    }

    @Override
    @LogExample
    @Transactional
    public User register(UserRegisterDto userRegisterDto) {
        if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent())
            throw new ResourceConflictException("Такой пользователь уже существует.");

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Стандартная роль не найдена."));
        User user = User.builder()
                .firstName(userRegisterDto.getFirstName())
                .secondName(userRegisterDto.getSecondName())
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .photoUrl("https://catalog-cdn.detmir.st/media/2fe02057f9915e72a378795d32c79ea9.jpeg")
                .role(role)
                .build();

        return userRepository.save(user);
    }

}
