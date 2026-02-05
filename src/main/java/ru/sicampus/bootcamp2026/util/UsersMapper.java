package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.entity.Users;

@UtilityClass
public class UsersMapper {
    public UsersDTO convertToDto(Users users){
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setId(users.getId());
        usersDTO.setName(users.getName());
        usersDTO.setLastName(users.getLastName());
        usersDTO.setLogin(users.getLogin());
        usersDTO.setEmail(users.getEmail());
        usersDTO.setPassword(users.getPassword());
        usersDTO.setDepartment(users.getDepartment());
        usersDTO.setPosition(users.getPosition());
        usersDTO.setPhotoUrl(users.getPhotoUrl());

        return usersDTO;
    }
}
