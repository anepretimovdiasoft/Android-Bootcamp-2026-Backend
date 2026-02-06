package ru.sicampus.bootcamp2026.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sicampus.bootcamp2026.dto.UserRegisterDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.entity.*;
import ru.sicampus.bootcamp2026.exception.EntityNotFoundException;
import ru.sicampus.bootcamp2026.exception.ValidationException;
import ru.sicampus.bootcamp2026.util.UserMapper;
import ru.sicampus.bootcamp2026.repository.*;
import ru.sicampus.bootcamp2026.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JobTitleRepository jobTitleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthorityRepository authorityRepository;

    @Override
    public List<UsersDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UsersDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDTO);
    }

    @Override
    public UsersDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с таким айди не найден: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UsersDTO createUser(UserRegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new ValidationException("Пароли не совпадают");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ValidationException("Почта уже используется");
        }

        JobTitle job = jobTitleRepository.findByTitleName(dto.getJobTitle())
                .orElseThrow(() -> new EntityNotFoundException("JobTitle не найден: " + dto.getJobTitle()));

        Department dept = departmentRepository.findByDeptName(dto.getDepartment())
                .orElseThrow(() -> new EntityNotFoundException("Department не найден: " + dto.getDepartment()));

        Optional<Authority> roleUser = authorityRepository.findByAuthority("ROLE_USER");
        if (roleUser.isEmpty()) {
            throw new RuntimeException("Authority not found");
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setJobTitle(job);
        user.setDepartment(dept);
        user.setAuthorities(Set.of(roleUser.get()));

        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UsersDTO updateProfile(Long id, UsersDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с таким айди не найден: " + id));

        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getContactInfo() != null) user.setContactInfo(dto.getContactInfo());
        if (dto.getAvatarUrl() != null) user.setAvatarUrl(dto.getAvatarUrl());

        if (dto.getJobTitle() != null) {
            JobTitle job = jobTitleRepository.findByTitleName(dto.getJobTitle())
                    .orElseThrow(() -> new EntityNotFoundException("JobTitle не найден: " + dto.getJobTitle()));
            user.setJobTitle(job);
        }
        if (dto.getDepartment() != null) {
            Department department = departmentRepository.findByDeptName(dto.getDepartment())
                    .orElseThrow(() -> new EntityNotFoundException("Department не найден: " + dto.getDepartment()));
            user.setDepartment(department);
        }

        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Пользователь с таким айди не найден: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UsersDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с такой почтой не найден: " + email));
        return userMapper.toDTO(user);
    }
}