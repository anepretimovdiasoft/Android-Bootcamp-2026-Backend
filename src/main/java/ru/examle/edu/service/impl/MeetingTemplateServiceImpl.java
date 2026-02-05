package ru.examle.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.MeetingTemplateDTO;
import ru.examle.edu.entity.MeetingTemplate;
import ru.examle.edu.repository.MeetingTemplateRepository;
import ru.examle.edu.service.MeetingTemplateService;
import ru.examle.edu.ulti.MeetingTemplateMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingTemplateServiceImpl implements MeetingTemplateService {

    private final MeetingTemplateRepository meetingTemplateRepository;
    private final MeetingTemplateMapper meetingTemplateMapper;

    @Override
    public List<MeetingTemplateDTO> getAllMeetingTemplates() {
        return meetingTemplateRepository.findAll().stream()
                .map(meetingTemplateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingTemplateDTO getMeetingTemplateById(Long id) {
        MeetingTemplate template = meetingTemplateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MeetingTemplate not found with id: " + id));
        return meetingTemplateMapper.toDTO(template);
    }

    @Override
    @Transactional
    public MeetingTemplateDTO createMeetingTemplate(MeetingTemplateDTO meetingTemplateDTO) {
        MeetingTemplate template = meetingTemplateMapper.toEntity(meetingTemplateDTO);
        MeetingTemplate savedTemplate = meetingTemplateRepository.save(template);
        return meetingTemplateMapper.toDTO(savedTemplate);
    }

    @Override
    @Transactional
    public MeetingTemplateDTO updateMeetingTemplate(Long id, MeetingTemplateDTO meetingTemplateDTO) {
        if (!meetingTemplateRepository.existsById(id)) {
            throw new RuntimeException("MeetingTemplate not found with id: " + id);
        }
        meetingTemplateDTO.setId(id);
        MeetingTemplate template = meetingTemplateMapper.toEntity(meetingTemplateDTO);
        MeetingTemplate updatedTemplate = meetingTemplateRepository.save(template);
        return meetingTemplateMapper.toDTO(updatedTemplate);
    }

    @Override
    @Transactional
    public void deleteMeetingTemplate(Long id) {
        if (!meetingTemplateRepository.existsById(id)) {
            throw new RuntimeException("MeetingTemplate not found with id: " + id);
        }
        meetingTemplateRepository.deleteById(id);
    }
}
