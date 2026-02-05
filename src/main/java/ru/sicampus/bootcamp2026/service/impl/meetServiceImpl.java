package ru.sicampus.bootcamp2026.service.impl;

import ru.sicampus.bootcamp2026.dto.meetDTO;
import ru.sicampus.bootcamp2026.entity.meet;
import ru.sicampus.bootcamp2026.exception.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.meetRepository;
import ru.sicampus.bootcamp2026.service.meetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class meetServiceImpl implements meetService {
    private final meetRepository meetRepository;

    @Override
    public meetDTO createMeet(meetDTO meetDTO) {
        meet meet = mapToEntity(meetDTO);
        meet savedMeet = meetRepository.save(meet);
        return mapToDTO(savedMeet);
    }

    @Override
    @Transactional(readOnly = true)
    public meetDTO getMeetById(Long id) {
        meet meet = meetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meet not found with id: " + id));
        return mapToDTO(meet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<meetDTO> getAllMeets() {
        return meetRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<meetDTO> getMeetsByDate(LocalDate date) {
        return meetRepository.findByMeetDate(date).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public meetDTO updateMeet(Long id, meetDTO meetDTO) {
        meet meet = meetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meet not found with id: " + id));

        meet.setTitle(meetDTO.getTitle());
        meet.setDescription(meetDTO.getDescription());
        meet.setMeetDate(meetDTO.getMeetDate());
        meet.setMeetTime(meetDTO.getMeetTime());

        meet updatedMeet = meetRepository.save(meet);
        return mapToDTO(updatedMeet);
    }

    @Override
    public void deleteMeet(Long id) {
        if (!meetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Meet not found with id: " + id);
        }
        meetRepository.deleteById(id);
    }

    private meet mapToEntity(meetDTO dto) {
        meet meet = new meet();
        meet.setTitle(dto.getTitle());
        meet.setDescription(dto.getDescription());
        meet.setMeetDate(dto.getMeetDate());
        meet.setMeetTime(dto.getMeetTime());
        return meet;
    }

    private meetDTO mapToDTO(meet meet) {
        meetDTO dto = new meetDTO();
        dto.setId(meet.getId());
        dto.setTitle(meet.getTitle());
        dto.setDescription(meet.getDescription());
        dto.setMeetDate(meet.getMeetDate());
        dto.setMeetTime(meet.getMeetTime());
        return dto;
    }
}