package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.dto.UserRegistrationDTO;

public interface UserService {
    Page<UserDTO> getAllUsers(Pageable pageable);
    Page<UserDTO> searchUsers(String query, Pageable pageable);
    UserDTO getUserById(Long id);
    UserDTO createUser(UserRegistrationDTO dto);
    UserDTO updateUser(Long id, UserDTO dto);
    UserDTO getUserByEmail(String email);
    UserDTO uploadPfp(Long id, MultipartFile file);
    void deleteUser(Long id);
}
