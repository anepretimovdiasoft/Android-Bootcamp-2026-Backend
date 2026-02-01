package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.aspect.annotation.LogExample;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exceptions.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.RoleRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.web.dto.user.UserUpdateDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @LogExample
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден."));
    }

    @Override
    @LogExample
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден."));
    }

    @Override
    @LogExample
    @Transactional(readOnly = true)
    public List<User> search(String search) {
        return userRepository.search(search);
    }

    @Override
    @LogExample
    @Transactional
    public User update(UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден."));

        user.setFirstName(userUpdateDto.getFirstName());
        user.setSecondName(userUpdateDto.getSecondName());
        user.setDescription(userUpdateDto.getDescription());
        user.setPosition(userUpdateDto.getPosition());
        user.setDepartment(userUpdateDto.getDepartment());

        return userRepository.save(user);
    }
}
