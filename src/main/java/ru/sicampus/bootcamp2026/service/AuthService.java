package ru.sicampus.bootcamp2026.service;


import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.RegisterDto;
import ru.sicampus.bootcamp2026.dto.UserDto;


@Service
public interface AuthService {
    UserDto register(RegisterDto dto);
}
