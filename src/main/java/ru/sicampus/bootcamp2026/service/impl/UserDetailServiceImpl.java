package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.entity.Person;
import ru.sicampus.bootcamp2026.exception.PersonNotFoundException;
import ru.sicampus.bootcamp2026.repository.PersonRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = personRepository.findByUsername(username);

        if (optionalPerson.isEmpty()) {
            throw new PersonNotFoundException();
        }

        return optionalPerson.get();
    }
}
