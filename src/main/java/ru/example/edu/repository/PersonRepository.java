package ru.example.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.edu.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
