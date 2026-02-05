package ru.examle.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.CompanySettingsDTO;
import ru.examle.edu.entity.CompanySettings;
import ru.examle.edu.repository.CompanySettingsRepository;
import ru.examle.edu.service.CompanySettingsService;
import ru.examle.edu.ulti.CompanySettingsMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanySettingsServiceImpl implements CompanySettingsService {

    private final CompanySettingsRepository companySettingsRepository;
    private final CompanySettingsMapper companySettingsMapper;

    @Override
    public List<CompanySettingsDTO> getAllCompanySettings() {
        return companySettingsRepository.findAll().stream()
                .map(companySettingsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompanySettingsDTO getCompanySettingsById(Long id) {
        CompanySettings settings = companySettingsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CompanySettings not found with id: " + id));
        return companySettingsMapper.toDTO(settings);
    }

    @Override
    @Transactional
    public CompanySettingsDTO createCompanySettings(CompanySettingsDTO companySettingsDTO) {
        CompanySettings settings = companySettingsMapper.toEntity(companySettingsDTO);
        CompanySettings savedSettings = companySettingsRepository.save(settings);
        return companySettingsMapper.toDTO(savedSettings);
    }

    @Override
    @Transactional
    public CompanySettingsDTO updateCompanySettings(Long id, CompanySettingsDTO companySettingsDTO) {
        if (!companySettingsRepository.existsById(id)) {
            throw new RuntimeException("CompanySettings not found with id: " + id);
        }
        companySettingsDTO.setId(id);
        CompanySettings settings = companySettingsMapper.toEntity(companySettingsDTO);
        CompanySettings updatedSettings = companySettingsRepository.save(settings);
        return companySettingsMapper.toDTO(updatedSettings);
    }

    @Override
    @Transactional
    public void deleteCompanySettings(Long id) {
        if (!companySettingsRepository.existsById(id)) {
            throw new RuntimeException("CompanySettings not found with id: " + id);
        }
        companySettingsRepository.deleteById(id);
    }
}
