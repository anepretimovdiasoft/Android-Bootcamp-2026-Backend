package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sicampus.bootcamp2026.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    @EntityGraph(attributePaths = {"loginData", "authorities"})
    Page<User> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"loginData", "authorities"})
//    @Query("""
//            SELECT DISTINCT u FROM User u
//            LEFT JOIN u.loginData ld
//            LEFT JOIN u.authorities a
//            WHERE ld.email = :email
//            """)
    List<User> findAll();

    @Query("""
            SELECT u
            FROM User u
            WHERE u.id IN :ids
            """)
    List<User> findAllByIds(@Param("ids") List<Long> ids);
}
