package ru.sicampus.bootcamp2026.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.MeetingType;
import java.util.Optional;

public interface MeetingTypeRepository extends JpaRepository<MeetingType, Long> {
    Optional<MeetingType> findByTypeName(String typeName);
}