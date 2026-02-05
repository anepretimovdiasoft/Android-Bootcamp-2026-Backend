package ru.sicampus.bootcamp2026.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.JobTitle;
import java.util.Optional;

public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
    Optional<JobTitle> findByTitleName(String titleName);
}