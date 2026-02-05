package ru.examle.edu.service;

import ru.examle.edu.dto.CompanySettingsDTO;
import java.util.List;

public interface CompanySettingsService {
    List<CompanySettingsDTO> getAllCompanySettings();
    CompanySettingsDTO getCompanySettingsById(Long id);
    CompanySettingsDTO createCompanySettings(CompanySettingsDTO companySettingsDTO);
    CompanySettingsDTO updateCompanySettings(Long id, CompanySettingsDTO companySettingsDTO);
    void deleteCompanySettings(Long id);
}
