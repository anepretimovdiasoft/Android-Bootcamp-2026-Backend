package ru.sicampus.bootcamp2026.service.impl;

import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sicampus.bootcamp2026.dto.fromApp.NewUserDTO;
import ru.sicampus.bootcamp2026.dto.toApp.UserDTO;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.LoginData;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.LoginDataEmailAlreadyTakenException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.LoginDataRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private LoginDataRepository loginDataRepository;
    @Mock
    private AuthorityRepository authorityRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void testGetUserByIdPositive() {
        User user = createUser(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDTO foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("test 1", foundUser.getFullName());
    }

    @Test
    void testGetUserByIdNegative() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(1L));

        assertTrue(exception.getMessage().contains("не найден"));
    }

    @Test
    void getUserByEmailPositive() {
        User user = createUser(1L);

        when(loginDataRepository.findByEmail("test1@gmail.com")).thenReturn(Optional.of(user.getLoginData()));
        UserDTO foundUser = userService.getUserByEmail("test1@gmail.com");

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("test 1", foundUser.getFullName());
    }

    @Test
    void getUserByEmailNegative() {
        when(loginDataRepository.findByEmail("test1@gmail.com")).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserByEmail("test1@gmail.com"));

        assertTrue(exception.getMessage().contains("не найден"));
    }

    @Test
    void getAllUsersPositive() {
        User user1 = createUser(1L);
        User user2 = createUser(2L);
        List<User> users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);
        List<UserDTO> foundUsers = userService.getAllUsers();

        assertEquals(2, foundUsers.size());
        assertEquals("test 1", foundUsers.get(0).getFullName());
        assertEquals("test 2", foundUsers.get(1).getFullName());
    }

    @Test
    void getAllUsersNegative() {
        when(userRepository.findAll()).thenReturn(List.of());
        List<UserDTO> foundUsers = userService.getAllUsers();

        assertNotNull(foundUsers);
        assertTrue(foundUsers.isEmpty());
    }

    @Test
    void createNewUserPositive() {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setEmail("test1@gmail.com");
        newUserDTO.setPassword("test1");
        newUserDTO.setFullName("test 1");
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        User user = createUser(1L);

        when(authorityRepository.findByAuthority("ROLE_USER")).thenReturn(Optional.of(authority));
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO newUser = userService.createUser(newUserDTO);

        assertNotNull(newUser);
        assertEquals("test 1", newUser.getFullName());
        assertEquals("test1@gmail.com", newUser.getEmail());
    }

    @Test
    void createNewUserNegative() {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setEmail("test1@gmail.com");
        newUserDTO.setPassword("test1");
        newUserDTO.setFullName("test 1");
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");

        when(authorityRepository.findByAuthority("ROLE_USER")).thenReturn(Optional.of(authority));
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(
                LoginDataEmailAlreadyTakenException.class,
                () -> userService.createUser(newUserDTO)
        );
    }

    @Test
    void updateUserInfoPositive() {
        UserDTO dto = new UserDTO();
        dto.setId(1L);
        dto.setFullName("test 999");
        dto.setEmail("test999@gmail.com");
        User user = createUser(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDTO foundUser = userService.updateUser(dto);

        assertNotNull(foundUser);
        assertEquals("test 999", foundUser.getFullName());
        assertEquals("test999@gmail.com", foundUser.getEmail());
    }

    @Test
    void updateUserInfoNegative() {
        UserDTO dto = new UserDTO();
        dto.setId(1L);
        dto.setEmail("test999@gmail.com");
        dto.setFullName("Updated Name");
        User user = createUser(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(loginDataRepository.existsByEmail("test999@gmail.com")).thenReturn(true);
        LoginDataEmailAlreadyTakenException exception = assertThrows(
                LoginDataEmailAlreadyTakenException.class,
                () -> userService.updateUser(dto)
        );

        assertTrue(exception.getMessage().contains("занят"));
    }

    @Test
    void getAllUsersPaginatedPositive() {
        User user1 = createUser(1L);
        User user2 = createUser(2L);
        Page<User> page = new PageImpl<>(List.of(user1, user2));

        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<UserDTO> pageData = userService.getAllUsersPaginated(0, 2);

        assertEquals(2, pageData.getContent().size());
        assertEquals("test 1", pageData.getContent().get(0).getFullName());
        assertEquals("test 2", pageData.getContent().get(1).getFullName());
    }

    @Test
    void getAllUsersPaginatedNegative() {
        Page<User> emptyPage = new PageImpl<>(List.of());

        when(userRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);
        Page<UserDTO> pageData = userService.getAllUsersPaginated(0, 2);

        assertNotNull(pageData);
        assertTrue(pageData.getContent().isEmpty());
    }
    private User createUser(Long id) {
        LoginData loginData = new LoginData();
        loginData.setEmail("test" + id + "@gmail.com");
        User user = new User();
        user.setId(id);
        user.setFullName("test " + id);
        user.setLoginData(loginData);

        return user;
    }

}
