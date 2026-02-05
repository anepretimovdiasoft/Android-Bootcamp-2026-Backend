package ru.sicampus.bootcamp2026.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.dtos.UserDto;
import ru.sicampus.bootcamp2026.entities.User;
import ru.sicampus.bootcamp2026.exeptions.UserNotFound;
import ru.sicampus.bootcamp2026.repositories.InvitationRepository;
import ru.sicampus.bootcamp2026.repositories.MeetingRepository;
import ru.sicampus.bootcamp2026.repositories.UserRepository;
import ru.sicampus.bootcamp2026.services.UserService;
import ru.sicampus.bootcamp2026.utils.UserMapper;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final InvitationRepository invitationRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFound("Пользователь с id " + id + " не найден!!!"));
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPosition(dto.getPosition());
        user.setPhotoUrl(dto.getPhotoUrl());

        user.setHash_password("temporary_password_hash");

        User savedUser = userRepository.save(user);
        return UserMapper.convertToDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("Не удалось обновить: пользователь не найден"));

        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPosition(dto.getPosition());
        user.setPhotoUrl(dto.getPhotoUrl());

        User updatedUser = userRepository.save(user);
        return UserMapper.convertToDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFound("Не удалось удалить: пользователь не найден");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserDetailsImpl.build(user);
    }
}
