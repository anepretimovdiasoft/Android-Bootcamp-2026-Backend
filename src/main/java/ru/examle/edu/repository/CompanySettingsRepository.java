package ru.examle.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.examle.edu.entity.CompanySettings;

public interface CompanySettingsRepository extends JpaRepository<CompanySettings, Long> {
}
