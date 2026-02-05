package ru.sicampus.bootcamp2026.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UserRegisterDTO;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.AuthorityNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserAlreadyExistsException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> getAllUsersPaginated(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserMapper::convertToDto);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsersBySurname(String surname) {
        return userRepository.findAllBySurname(surname).stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsersByName(String name) {
        return userRepository.findAllByName(name).stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsersByPatronymic(String patronymic) {
        return userRepository.findAllByPatronymic(patronymic).stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsersByDepartmentName(String departmentName) {
        return userRepository.findAllByDepartmentName(departmentName).stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(UserRegisterDTO dto) {
        User user = new User();

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + dto.getUsername() + " already exists");
        }

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setInvites(new ArrayList<>());
        user.setAuthorities(Set.of(authorityRepository.findByAuthority("ROLE_USER")
                .orElseThrow(() -> new AuthorityNotFoundException("Authority not found"))));

        return UserMapper.convertToDto(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setSurname(dto.getSurname());
        user.setName(dto.getName());
        user.setPatronymic(dto.getPatronymic());
        user.setDepartmentName(dto.getDepartmentName());
        user.setPhotoUrl(dto.getPhotoUrl());
        user.setMessengerLink(dto.getMessengerLink());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());

        List<Invitation> updatedInvitations = new ArrayList<>();
        List<Long> updatedInvitedMeetingIds = dto.getInvitedMeetingIds();
        if (!(updatedInvitedMeetingIds == null)) {
            updatedInvitedMeetingIds.forEach(meetingId -> {
                Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
                Invitation newInvitation = new Invitation();
                newInvitation.setInvitedUser(user);
                newInvitation.setMeeting(meeting);
                newInvitation.setAccepted(false);
                updatedInvitations.add(newInvitation);
            });
        }

        user.getInvites().clear();
        user.getInvites().addAll(updatedInvitations);

        return UserMapper.convertToDto(userRepository.save(user));
    }

    @Override
    public UserDTO patchUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (dto.getSurname() != null) {
            user.setSurname(dto.getSurname());
        }
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getPatronymic() != null) {
            user.setPatronymic(dto.getPatronymic());
        }
        if (dto.getDepartmentName() != null) {
            user.setDepartmentName(dto.getDepartmentName());
        }
        if (dto.getPhotoUrl() != null) {
            user.setPhotoUrl(dto.getPhotoUrl());
        }
        if (dto.getMessengerLink() != null) {
            user.setMessengerLink(dto.getMessengerLink());
        }
        if (dto.getPhoneNumber() != null) {
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        List<Invitation> updatedInvitations = new ArrayList<>();
        List<Long> updatedInvitedMeetingIds = dto.getInvitedMeetingIds();
        if (!(updatedInvitedMeetingIds == null)) {
            updatedInvitedMeetingIds.forEach(meetingId -> {
                Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
                Invitation newInvitation = new Invitation();
                newInvitation.setInvitedUser(user);
                newInvitation.setMeeting(meeting);
                newInvitation.setAccepted(false);
                updatedInvitations.add(newInvitation);
            });
        }

        user.getInvites().clear();
        user.getInvites().addAll(updatedInvitations);

        return UserMapper.convertToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
