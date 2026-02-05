package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.meetDTO;
import java.time.LocalDate;
import java.util.List;

public interface meetService {
    meetDTO createMeet(meetDTO meetDTO);
    meetDTO getMeetById(Long id);
    List<meetDTO> getAllMeets();
    List<meetDTO> getMeetsByDate(LocalDate date);
    meetDTO updateMeet(Long id, meetDTO meetDTO);
    void deleteMeet(Long id);
}