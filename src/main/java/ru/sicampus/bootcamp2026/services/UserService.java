package ru.sicampus.bootcamp2026.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto createUser(UserDto dto);
    UserDto updateUser(Long id, UserDto dto);
    void deleteUser(Long id);

    Page<UserDto> getAllUsersPaginated(Pageable pageable);
}
