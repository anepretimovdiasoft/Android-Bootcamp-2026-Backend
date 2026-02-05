package ru.examle.edu.ulti;

import org.springframework.stereotype.Component;
import ru.examle.edu.dto.UserDTO;
import ru.examle.edu.entity.User;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        // Password hash should not be exposed
        dto.setFullName(user.getFullName());
        dto.setPosition(user.getPosition());
        dto.setDepartment(user.getDepartment());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setRole(user.getRole());
        dto.setNotificationSettings(user.getNotificationSettings());
        dto.setWorkHoursStart(user.getWorkHoursStart());
        dto.setWorkHoursEnd(user.getWorkHoursEnd());
        dto.setActive(user.isActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        if (dto.getId() != null) user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPasswordHash());
        user.setFullName(dto.getFullName());
        user.setPosition(dto.getPosition());
        user.setDepartment(dto.getDepartment());
        user.setAvatarUrl(dto.getAvatarUrl());
        if (dto.getRole() != null) user.setRole(dto.getRole());
        user.setNotificationSettings(dto.getNotificationSettings());
        if (dto.getWorkHoursStart() != null) user.setWorkHoursStart(dto.getWorkHoursStart());
        if (dto.getWorkHoursEnd() != null) user.setWorkHoursEnd(dto.getWorkHoursEnd());
        user.setActive(dto.isActive());
        return user;
    }
}
