package ru.examle.edu.ulti;

import org.springframework.stereotype.Component;
import ru.examle.edu.dto.MeetingTemplateDTO;
import ru.examle.edu.entity.MeetingTemplate;

@Component
public class MeetingTemplateMapper {
    public MeetingTemplateDTO toDTO(MeetingTemplate template) {
        if (template == null) return null;
        MeetingTemplateDTO dto = new MeetingTemplateDTO();
        dto.setId(template.getId());
        dto.setName(template.getName());
        dto.setDescription(template.getDescription());
        dto.setTitlePattern(template.getTitlePattern());
        dto.setDefaultDurationHours(template.getDefaultDurationHours());
        dto.setDefaultPriority(template.getDefaultPriority());
        dto.setRequiredParticipants(template.getRequiredParticipants());
        dto.setSuggestedRooms(template.getSuggestedRooms());
        dto.setAgendaTemplate(template.getAgendaTemplate());
        dto.setCompanyTemplate(template.isCompanyTemplate());
        dto.setDepartment(template.getDepartment());
        dto.setCreatedBy(template.getCreatedBy());
        dto.setCreatedAt(template.getCreatedAt());
        dto.setUpdatedAt(template.getUpdatedAt());
        return dto;
    }

    public MeetingTemplate toEntity(MeetingTemplateDTO dto) {
        if (dto == null) return null;
        MeetingTemplate template = new MeetingTemplate();
        if (dto.getId() != null) template.setId(dto.getId());
        template.setName(dto.getName());
        template.setDescription(dto.getDescription());
        template.setTitlePattern(dto.getTitlePattern());
        if (dto.getDefaultDurationHours() != null) template.setDefaultDurationHours(dto.getDefaultDurationHours());
        if (dto.getDefaultPriority() != null) template.setDefaultPriority(dto.getDefaultPriority());
        template.setRequiredParticipants(dto.getRequiredParticipants());
        template.setSuggestedRooms(dto.getSuggestedRooms());
        template.setAgendaTemplate(dto.getAgendaTemplate());
        template.setCompanyTemplate(dto.isCompanyTemplate());
        template.setDepartment(dto.getDepartment());
        template.setCreatedBy(dto.getCreatedBy());
        return template;
    }
}
