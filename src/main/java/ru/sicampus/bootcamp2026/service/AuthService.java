package ru.sicampus.bootcamp2026.service;

import org.springframework.security.core.Authentication;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.web.dto.user.UserRegisterDto;

public interface AuthService {

    User login(Authentication authentication);
    User register(UserRegisterDto userRegisterDto);

}
