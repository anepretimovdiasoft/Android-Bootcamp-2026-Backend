package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.dto.fromApp.NewUserDTO;
import ru.sicampus.bootcamp2026.dto.toApp.UserDTO;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.LoginData;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.AuthorityNotFoundException;
import ru.sicampus.bootcamp2026.exception.LoginDataEmailAlreadyTakenException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.LoginDataRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LoginDataRepository loginDataRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("Пользователь (id: " + id + ") не найден."));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        Optional<LoginData> loginData = loginDataRepository.findByEmail(email);

        if (loginData.isEmpty()) {
            throw new UserNotFoundException("Пользователь c почтой (Email: " + email + ") не найден.");
        }

        User user = loginData.get().getUser();
        return UserMapper.convertToDto(user);
    }

    @Override
    @Transactional
    public UserDTO createUser(NewUserDTO dto) {
        String userRole = "ROLE_USER";
        Authority authority = authorityRepository.findByAuthority(userRole).orElseThrow(() ->
                new AuthorityNotFoundException("Не найдена роль: " + userRole));

        User user = UserMapper.convertToDomain(dto, passwordEncoder);
        user.setAuthorities(Set.of(authority));

        try {
            return UserMapper.convertToDto(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new LoginDataEmailAlreadyTakenException("Email (" + dto.getEmail() + ") уже занят.");
        }
    }

    @Override
    @Transactional
    public UserDTO updateUser(UserDTO dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(() ->
                        new UserNotFoundException("Пользователь (id: " + dto.getId() + ") не найден."));

        LoginData loginData = user.getLoginData();
        if (!loginData.getEmail().equals(dto.getEmail())) {
            if (loginDataRepository.existsByEmail(dto.getEmail())) {
                throw new LoginDataEmailAlreadyTakenException("Email (" + dto.getEmail() + ") уже занят.");
            }

            loginData.setEmail(dto.getEmail());
        }
        user.setFullName(dto.getFullName());

        return UserMapper.convertToDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь (id: " + id + ") не найден.");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(UserMapper::convertToDto);
    }
}
