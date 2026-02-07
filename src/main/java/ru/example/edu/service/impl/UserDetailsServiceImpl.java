package ru.example.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.example.edu.entity.Person;
import ru.example.edu.exception.PersonNotFoundException;
import ru.example.edu.repository.PersonRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = personRepository.findByLogin(username);

        if (optionalPerson.isEmpty()) {
            throw new PersonNotFoundException("Person not found!");
        }
        return optionalPerson.get();
    }
}
