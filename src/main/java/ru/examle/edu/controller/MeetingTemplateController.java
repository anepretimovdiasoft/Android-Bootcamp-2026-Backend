package ru.examle.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.examle.edu.dto.MeetingTemplateDTO;
import ru.examle.edu.service.MeetingTemplateService;

import java.util.List;

@RestController
@RequestMapping("/api/meeting-templates")
@RequiredArgsConstructor
public class MeetingTemplateController {

    private final MeetingTemplateService meetingTemplateService;

    @GetMapping
    public List<MeetingTemplateDTO> getAllMeetingTemplates() {
        return meetingTemplateService.getAllMeetingTemplates();
    }

    @GetMapping("/{id}")
    public MeetingTemplateDTO getMeetingTemplateById(@PathVariable Long id) {
        return meetingTemplateService.getMeetingTemplateById(id);
    }

    @PostMapping
    public MeetingTemplateDTO createMeetingTemplate(@RequestBody MeetingTemplateDTO meetingTemplateDTO) {
        return meetingTemplateService.createMeetingTemplate(meetingTemplateDTO);
    }

    @PutMapping("/{id}")
    public MeetingTemplateDTO updateMeetingTemplate(@PathVariable Long id, @RequestBody MeetingTemplateDTO meetingTemplateDTO) {
        return meetingTemplateService.updateMeetingTemplate(id, meetingTemplateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMeetingTemplate(@PathVariable Long id) {
        meetingTemplateService.deleteMeetingTemplate(id);
    }
}
