package ru.examle.edu.ulti;

import org.springframework.stereotype.Component;
import ru.examle.edu.dto.CompanySettingsDTO;
import ru.examle.edu.entity.CompanySettings;

@Component
public class CompanySettingsMapper {
    public CompanySettingsDTO toDTO(CompanySettings settings) {
        if (settings == null) return null;
        CompanySettingsDTO dto = new CompanySettingsDTO();
        dto.setId(settings.getId());
        dto.setSettingKey(settings.getSettingKey());
        dto.setSettingValue(settings.getSettingValue());
        dto.setSettingType(settings.getSettingType());
        dto.setCategory(settings.getCategory());
        dto.setDescription(settings.getDescription());
        dto.setEditable(settings.isEditable());
        dto.setCreatedAt(settings.getCreatedAt());
        dto.setUpdatedAt(settings.getUpdatedAt());
        return dto;
    }

    public CompanySettings toEntity(CompanySettingsDTO dto) {
        if (dto == null) return null;
        CompanySettings settings = new CompanySettings();
        if (dto.getId() != null) settings.setId(dto.getId());
        settings.setSettingKey(dto.getSettingKey());
        settings.setSettingValue(dto.getSettingValue());
        settings.setSettingType(dto.getSettingType());
        settings.setCategory(dto.getCategory());
        settings.setDescription(dto.getDescription());
        settings.setEditable(dto.isEditable());
        return settings;
    }
}
