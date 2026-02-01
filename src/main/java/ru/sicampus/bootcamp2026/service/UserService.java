package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.web.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {

    User getById(Long id);
    User getByEmail(String email);

    List<User> search(String search);

    User update(UserUpdateDto userUpdateDto);

}
