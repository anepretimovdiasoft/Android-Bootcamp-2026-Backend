package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.dto.UserRegisterDTO;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.exceptions.EmailAlreadyUsedException;
import ru.sicampus.bootcamp2026.exceptions.PasswordNotMatchException;
import ru.sicampus.bootcamp2026.exceptions.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.UsersRepository;
import ru.sicampus.bootcamp2026.service.UsersService;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return usersRepository.findAll()
                .stream()
                .map(UserMapper::convertToDto)
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        return usersRepository.findById(id).map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserDTO createUser(UserRegisterDTO dto) {
        Optional<Users> optionalUser = usersRepository.findByEmail(dto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyUsedException("User with this email already exists");
        }
        if (!dto.getPassword().equals(dto.getPasswordAgain())) {
            throw new PasswordNotMatchException("Passwords not matched");
        }
        Authority roleUser = authorityRepository.findByAuthority("ROLE_USER").orElseThrow(() -> new RuntimeException("Role User not exist"));

        Users user = new Users();
        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setPatronymic(dto.getPatronymic());
        user.setEmail(dto.getEmail());
        user.setPosition(dto.getPosition());
        user.setHashPassword(passwordEncoder.encode(dto.getPassword()));
        user.getAuthorities().add(roleUser);
        user.setCreatedAt(LocalDateTime.now());
        return UserMapper.convertToDto(usersRepository.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not exist"));

        if (!user.getEmail().equals(dto.getEmail()) && usersRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException("Your new email already use another user");
        }

        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setPatronymic(dto.getPatronymic());
        user.setEmail(dto.getEmail());
        user.setPosition(dto.getPosition());
        return UserMapper.convertToDto(usersRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if(usersRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("User not exists");
        }

        usersRepository.deleteById(id);
    }

    @Override
    public UserDTO getPersonByEmail(String email) {
        return UserMapper.convertToDto(usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Not user with this email")
        ));
    }

    @Override
    public Page<UserDTO> getAllUsersPaginated(Pageable pageable) {
        return usersRepository.findAll(pageable).map(UserMapper::convertToDto);
    }

    @Override
    public Pageable buildPage(int page, int size) {
        return PageRequest.of(page, size);
    }
}
