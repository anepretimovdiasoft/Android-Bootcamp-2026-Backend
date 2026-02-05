package ru.examle.edu.service;

import ru.examle.edu.dto.MeetingTemplateDTO;
import java.util.List;

public interface MeetingTemplateService {
    List<MeetingTemplateDTO> getAllMeetingTemplates();
    MeetingTemplateDTO getMeetingTemplateById(Long id);
    MeetingTemplateDTO createMeetingTemplate(MeetingTemplateDTO meetingTemplateDTO);
    MeetingTemplateDTO updateMeetingTemplate(Long id, MeetingTemplateDTO meetingTemplateDTO);
    void deleteMeetingTemplate(Long id);
}
