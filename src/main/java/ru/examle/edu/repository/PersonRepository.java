package ru.examle.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.examle.edu.enity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
