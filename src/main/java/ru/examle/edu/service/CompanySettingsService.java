package ru.examle.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.examle.edu.dto.CompanySettingsDTO;

public interface CompanySettingsService {
    Page<CompanySettingsDTO> getAllCompanySettings(Pageable pageable);
    CompanySettingsDTO getCompanySettingsById(Long id);
    CompanySettingsDTO createCompanySettings(CompanySettingsDTO companySettingsDTO);
    CompanySettingsDTO updateCompanySettings(Long id, CompanySettingsDTO companySettingsDTO);
    void deleteCompanySettings(Long id);
}
