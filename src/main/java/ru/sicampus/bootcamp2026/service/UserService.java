package ru.sicampus.bootcamp2026.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.BadRequestException;
import ru.sicampus.bootcamp2026.exception.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> searchUsers(String search, Pageable pageable) {
        return userRepository.searchUsers(search, pageable);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Authority userRole = authorityRepository.findByAuthority("ROLE_USER")
                .orElseGet(() -> {
                    Authority newRole = new Authority();
                    newRole.setAuthority("ROLE_USER");
                    return authorityRepository.save(newRole);
                });

        user.setAuthorities(new HashSet<>());
        user.getAuthorities().add(userRole);

        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setPhotoUrl(userDetails.getPhotoUrl());
        user.setPosition(userDetails.getPosition());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}