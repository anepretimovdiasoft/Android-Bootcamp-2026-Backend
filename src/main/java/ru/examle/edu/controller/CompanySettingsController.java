package ru.examle.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.examle.edu.dto.CompanySettingsDTO;
import ru.examle.edu.service.CompanySettingsService;

@RestController
@RequestMapping("/api/company-settings")
@RequiredArgsConstructor
public class CompanySettingsController {

    private final CompanySettingsService companySettingsService;

    @GetMapping
    public Page<CompanySettingsDTO> getAllCompanySettings(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int size) {
        return companySettingsService.getAllCompanySettings(PageRequest.of(page, size));
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
