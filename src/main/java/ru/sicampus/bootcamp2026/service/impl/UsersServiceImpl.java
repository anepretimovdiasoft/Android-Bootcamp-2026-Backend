package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.exeption.UsersNotFoundExeptions;
import ru.sicampus.bootcamp2026.repository.MeetingsRepository;
import ru.sicampus.bootcamp2026.repository.UsersRepository;
import ru.sicampus.bootcamp2026.service.UsersService;
import ru.sicampus.bootcamp2026.util.UsersMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final MeetingsRepository meetingsRepository;

    @Override
    public List<UsersDTO> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(UsersMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsersDTO getUsersById(Long id) {
        return usersRepository.findById(id).map(UsersMapper::convertToDto)
                .orElseThrow(()-> new UsersNotFoundExeptions("User not found!!!"));
    }

    @Override
    public UsersDTO createUsers(UsersDTO dto) {

        Users users = new Users();
        users.setName(dto.getName());
        users.setLastName(dto.getLastName());
        users.setPhoneNumber(dto.getPhoneNumber());
        users.setLogin(dto.getLogin());
        users.setEmail(dto.getEmail());
        users.setPassword(dto.getPassword());
        users.setDepartment(dto.getDepartment());
        users.setPosition(dto.getPosition());
        users.setPhotoUrl(dto.getPhotoUrl());

        return UsersMapper.convertToDto(usersRepository.save(users));
    }

    @Override
    public UsersDTO updateUsers(Long id, UsersDTO dto) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new UsersNotFoundExeptions("User not found!!!"));

        users.setName(dto.getName());
        users.setLastName(dto.getLastName());
        users.setPhoneNumber(dto.getPhoneNumber());
        users.setLogin(dto.getLogin());
        users.setEmail(dto.getEmail());
        users.setPassword(dto.getPassword());
        users.setDepartment(dto.getDepartment());
        users.setPosition(dto.getPosition());
        users.setPhotoUrl(dto.getPhotoUrl());

        return UsersMapper.convertToDto(usersRepository.save(users));
    }

    @Override
    public void deleteUsers(Long id) {
        usersRepository.deleteById(id);
    }
}
