package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.request.UserCreateDTO;
import ru.sicampus.bootcamp2026.dto.response.UserResponseDTO;
import ru.sicampus.bootcamp2026.dto.request.UserUpdateDTO;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.UserExistsException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.mapper.UserMapper;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder encoder;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(UserMapper::convertToDto)
                .toList();
    }

    @Override
    public List<UserResponseDTO> searchUsers(String query) {
        return repository.findByEmailContaining(query).stream().map(UserMapper::convertToDto).toList();
    }

    @Override
    public UserResponseDTO getUserById(long id) throws UserNotFoundException {
        return repository.findById(id)
                .map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) throws UserNotFoundException {
        return repository.findByEmail(email)
                .map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public UserResponseDTO createUser(UserCreateDTO dto) throws UserExistsException {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserExistsException(dto.getEmail());
        }


        Authority userRole = authorityRepository.findByAuthority("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found in DB"));

        User newUser = new User(
                dto.getEmail(),
                dto.getFullName(),
                encoder.encode(dto.getPassword())
        );
        newUser.setAuthorities(Set.of(userRole));

        // Сохраняем и возвращаем DTO
        return UserMapper.convertToDto(repository.save(newUser));
    }


    @Override
    public UserResponseDTO updateUser(long id, UserUpdateDTO dto) throws UserNotFoundException {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getAvatarUrl() != null) user.setAvatarUrl(dto.getAvatarUrl());
        return UserMapper.convertToDto(repository.save(user));
    }

    @Override
    public void deleteUser(long id) {
        repository.deleteById(id);
    }
}
