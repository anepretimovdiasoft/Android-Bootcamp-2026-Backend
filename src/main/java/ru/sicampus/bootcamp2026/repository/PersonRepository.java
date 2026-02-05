package ru.sicampus.bootcamp2026.repository;

import java.util.Optional;
import ru.sicampus.bootcamp2026.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
}
