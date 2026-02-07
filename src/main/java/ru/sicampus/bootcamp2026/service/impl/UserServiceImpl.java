package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.entity.Position;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.user.PositionNotFoundException;
import ru.sicampus.bootcamp2026.exception.user.UserAlreadyExist;
import ru.sicampus.bootcamp2026.exception.user.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.PositionRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PositionRepository positionRepository;


    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserDto createUser(UserDto dto) {
        if (userRepository.existsByLogin(dto.getLogin())){
            throw new UserAlreadyExist("User with this login already exists");
        }
        User user = createUserFromDto(new User(), dto);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(Long id, UserDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User updatedUser = createUserFromDto(user, dto);
        return UserMapper.toDto(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User createUserFromDto(User user, UserDto dto){
        Optional<Position> optionalPosition = positionRepository.findByPosition(dto.getPosition());
        user.setPosition(optionalPosition.orElseThrow(() -> new PositionNotFoundException("Position not found")));

        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setAboutMe(dto.getAboutMe());
        user.setPhotoUrl(dto.getPhotoUrl());
        user.setPassword("temp");
        return user;
    }

}
