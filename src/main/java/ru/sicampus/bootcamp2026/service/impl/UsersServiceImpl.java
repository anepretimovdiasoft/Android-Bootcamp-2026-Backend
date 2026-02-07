package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.dto.UsersRegisterDTO;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.exeption.PersonAlreadyExistException;
import ru.sicampus.bootcamp2026.exeption.UsersNotFoundExeptions;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.MeetingsRepository;
import ru.sicampus.bootcamp2026.repository.UsersRepository;
import ru.sicampus.bootcamp2026.service.UsersService;
import ru.sicampus.bootcamp2026.util.UsersMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final MeetingsRepository meetingsRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UsersDTO> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(UsersMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsersDTO getUsersById(Long id) {
        return usersRepository.findById(id).map(UsersMapper::convertToDto)
                .orElseThrow(() -> new UsersNotFoundExeptions("User not found!!!"));
    }

    @Override
    public UsersDTO createUsers(UsersRegisterDTO dto) {

        if (usersRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new  PersonAlreadyExistException("Login already exist exception!!!");
        }


        Optional<Authority> roleUser = authorityRepository.findByAuthority("ROLE_USER");

        if (roleUser.isEmpty()){
            throw new RuntimeException("Authority not found!!!");
        }

        Users users = new Users();
        users.setLogin(dto.getLogin());
        users.setName(dto.getName());
        users.setLastName(dto.getLastName());
        users.setPhoneNumber(dto.getPhoneNumber());
        users.setEmail(dto.getEmail());
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        users.setAuthorities(Set.of(roleUser.get()));
        //ниже то что убралось из-за того что добавился UserRegisterDTO
//        users.setDepartment(dto.getDepartment());
//        users.setPosition(dto.getPosition());
//        users.setPhotoUrl(dto.getPhotoUrl());

        return UsersMapper.convertToDto(usersRepository.save(users));
    }

    @Override
    public UsersDTO updateUsers(Long id, UsersDTO dto) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new UsersNotFoundExeptions("User not found!!!"));

        users.setName(dto.getName());
        users.setLastName(dto.getLastName());
        users.setPhoneNumber(dto.getPhoneNumber());
     // users.setLogin(dto.getLogin());
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

    @Override
    public UsersDTO getUsersByLogin(String login) {
        Optional<Users> optionalUsers = usersRepository.findByLogin(login);
        if (optionalUsers.isEmpty()){
            throw new UsersNotFoundExeptions("User with login " + login + " not found");
        }

        return UsersMapper.convertToDto(optionalUsers.get());
    }

    @Override
    public Page<UsersDTO> getAllUsersPaginated(Pageable pageable) {
        return usersRepository.findAll(pageable).map(UsersMapper::convertToDto);
    }
}
