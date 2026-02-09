package ru.examle.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.examle.edu.dto.MeetingTemplateDTO;
import ru.examle.edu.service.MeetingTemplateService;

@RestController
@RequestMapping("/api/meeting-templates")
@RequiredArgsConstructor
public class MeetingTemplateController {

    private final MeetingTemplateService meetingTemplateService;

    @GetMapping
    public Page<MeetingTemplateDTO> getAllMeetingTemplates(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20") int size) {
        return meetingTemplateService.getAllMeetingTemplates(PageRequest.of(page, size));
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
