package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UserRegisterDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.exception.UsernameIsAlreadyTakenException;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.InvitationsRepository;
import ru.sicampus.bootcamp2026.repository.UsersRepository;
import ru.sicampus.bootcamp2026.service.UsersService;
import ru.sicampus.bootcamp2026.util.UsersMapper;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final InvitationsRepository invitationsRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsersDTO getUserById(long id) {
        return usersRepository.findById(id).map(UsersMapper::convertToDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UsersDTO createUser(UserRegisterDTO dto) {
        if (usersRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameIsAlreadyTakenException("Username is already taken");
        }

        Optional<Authority> roleUser = authorityRepository.findByAuthority("ROLE_USER");
        if(roleUser.isEmpty()) {
            throw new RuntimeException("Authority not found");
        }

        Users user = new Users();
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUsername(dto.getUsername());
        user.setAuthorities(Set.of(roleUser.get()));

        return UsersMapper.convertToDTO(usersRepository.save(user));
    }

    @Override
    public UsersDTO getUserByUsername(String username) {
        return UsersMapper.convertToDTO(usersRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User not found")));
    }

    @Override
    public UsersDTO updateUser(long id, UsersDTO usersDTO) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setId(usersDTO.getId());
        user.setName(usersDTO.getName());
        user.setAvatarUrl(usersDTO.getAvatarUrl());
        return UsersMapper.convertToDTO(usersRepository.save(user));
    }

    @Override
    public void deleteUser(long id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
//        List<Invitations> userInvitations = invitationsRepository.findAllByInvitedUserId(user);
//        invitationsRepository.deleteAll(userInvitations);
        usersRepository.delete(user);
    }
}