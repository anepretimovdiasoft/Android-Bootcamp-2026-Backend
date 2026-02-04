package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.repository.UsersRepository;

import java.nio.file.ProviderNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = usersRepository.findByLogin(username);

        if (optionalUsers.isEmpty()){
            throw new ProviderNotFoundException("Person not found!!");

        }

        return (UserDetails) optionalUsers.get();
    }
}
