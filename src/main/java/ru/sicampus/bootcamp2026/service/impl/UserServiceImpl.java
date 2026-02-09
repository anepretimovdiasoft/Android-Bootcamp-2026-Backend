package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.dto.UserRegisterDto;
import ru.sicampus.bootcamp2026.entity.Position;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.user.PositionNotFoundException;
import ru.sicampus.bootcamp2026.exception.user.UserAlreadyExist;
import ru.sicampus.bootcamp2026.exception.user.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.PositionRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public UserDto createUser(UserRegisterDto dto) {
        if (userRepository.existsByLogin(dto.getLogin())){
            throw new UserAlreadyExist("User with this login already exists");
        }

        User user = new User();
        Optional<Position> optionalPosition = positionRepository.findByPosition(dto.getPosition());
        user.setPosition(optionalPosition.orElseThrow(() -> new PositionNotFoundException("Position not found")));

        user.setLogin(dto.getLogin());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());

        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto updateUser(String login, UserDto dto) {

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Optional<Position> optionalPosition = positionRepository.findByPosition(dto.getPosition());
        user.setPosition(optionalPosition.orElseThrow(() -> new PositionNotFoundException("Position not found")));
        Optional<User> tmp = userRepository.findByLogin(dto.getLogin());
        if (!(tmp.isEmpty() || user.equals(tmp.get()))){
            throw new UserAlreadyExist("User with this login already exist");
        }
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setAboutMe(dto.getAboutMe());
        user.setPhotoUrl(dto.getPhotoUrl());

        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(String login) {
        userRepository.deleteUserByLogin(login);
    }

    @Override
    public UserDto getUserByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return UserMapper.toDto(user.get());
    }

}
