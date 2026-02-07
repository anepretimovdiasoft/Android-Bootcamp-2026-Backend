package ru.sicampus.bootcamp2026.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.Entity.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar,Long> {
    Avatar findById(long id);
    Avatar findByName(String name);
}
