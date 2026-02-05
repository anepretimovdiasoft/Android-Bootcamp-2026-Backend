package ru.sicampus.bootcamp2026.util;

import org.springframework.stereotype.Component;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.dto.UserRegisterDTO;
import ru.sicampus.bootcamp2026.entity.User;


@Component
public class UserMapper {

    public static UserDTO toDto(User user) {
        if (user == null) return null;

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.isEnabled()
        );
    }

    public static User toEntity(UserRegisterDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEnabled(true);

        return user;
    }
}