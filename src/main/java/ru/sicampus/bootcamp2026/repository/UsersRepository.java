package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Override
    Page<Users> findAll(Pageable pageable);

    Optional<Users> findByLogin(String login);
}
