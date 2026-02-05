package ru.examle.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.examle.edu.dto.CompanySettingsDTO;
import ru.examle.edu.service.CompanySettingsService;

import java.util.List;

@RestController
@RequestMapping("/api/company-settings")
@RequiredArgsConstructor
public class CompanySettingsController {

    private final CompanySettingsService companySettingsService;

    @GetMapping
    public List<CompanySettingsDTO> getAllCompanySettings() {
        return companySettingsService.getAllCompanySettings();
    }

    @GetMapping("/{id}")
    public CompanySettingsDTO getCompanySettingsById(@PathVariable Long id) {
        return companySettingsService.getCompanySettingsById(id);
    }

    @PostMapping
    public CompanySettingsDTO createCompanySettings(@RequestBody CompanySettingsDTO companySettingsDTO) {
        return companySettingsService.createCompanySettings(companySettingsDTO);
    }

    @PutMapping("/{id}")
    public CompanySettingsDTO updateCompanySettings(@PathVariable Long id, @RequestBody CompanySettingsDTO companySettingsDTO) {
        return companySettingsService.updateCompanySettings(id, companySettingsDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCompanySettings(@PathVariable Long id) {
        companySettingsService.deleteCompanySettings(id);
    }
}
