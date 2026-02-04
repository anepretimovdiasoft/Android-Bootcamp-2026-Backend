package ru.sicampus.bootcamp2026.util;
import org.springframework.stereotype.Component;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.entity.User;

@Component
public class UserMapper {

    public UsersDTO toDTO(User user) {
        if (user == null) return null;

        UsersDTO dto = new UsersDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setAge(user.getAge());
        dto.setContactInfo(user.getContactInfo());
        dto.setAvatarUrl(user.getAvatarUrl());

        if (user.getJobTitle() != null) {
            dto.setJobTitle(user.getJobTitle().getTitleName());
        }
        if (user.getDepartment() != null) {
            dto.setDepartment(user.getDepartment().getDeptName());
        }
        return dto;
    }
}