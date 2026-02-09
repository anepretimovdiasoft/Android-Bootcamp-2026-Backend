package ru.examle.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.examle.edu.dto.MeetingTemplateDTO;

public interface MeetingTemplateService {
    Page<MeetingTemplateDTO> getAllMeetingTemplates(Pageable pageable);
    MeetingTemplateDTO getMeetingTemplateById(Long id);
    MeetingTemplateDTO createMeetingTemplate(MeetingTemplateDTO meetingTemplateDTO);
    MeetingTemplateDTO updateMeetingTemplate(Long id, MeetingTemplateDTO meetingTemplateDTO);
    void deleteMeetingTemplate(Long id);
}
