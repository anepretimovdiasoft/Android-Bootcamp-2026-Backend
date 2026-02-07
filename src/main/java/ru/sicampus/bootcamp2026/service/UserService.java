package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.web.dto.user.UserUpdateDto;

public interface UserService {

    User getById(Long id);
    User getByEmail(String email);

    Page<User> search(String search, Pageable pageable);

    User update(UserUpdateDto userUpdateDto);

}
