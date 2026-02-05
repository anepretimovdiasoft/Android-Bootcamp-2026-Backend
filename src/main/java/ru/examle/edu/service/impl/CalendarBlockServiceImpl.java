package ru.examle.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.CalendarBlockDTO;
import ru.examle.edu.entity.CalendarBlock;
import ru.examle.edu.repository.CalendarBlockRepository;
import ru.examle.edu.service.CalendarBlockService;
import ru.examle.edu.ulti.CalendarBlockMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarBlockServiceImpl implements CalendarBlockService {

    private final CalendarBlockRepository calendarBlockRepository;
    private final CalendarBlockMapper calendarBlockMapper;

    @Override
    public List<CalendarBlockDTO> getAllCalendarBlocks() {
        return calendarBlockRepository.findAll().stream()
                .map(calendarBlockMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CalendarBlockDTO getCalendarBlockById(Long id) {
        CalendarBlock block = calendarBlockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CalendarBlock not found with id: " + id));
        return calendarBlockMapper.toDTO(block);
    }

    @Override
    @Transactional
    public CalendarBlockDTO createCalendarBlock(CalendarBlockDTO calendarBlockDTO) {
        CalendarBlock block = calendarBlockMapper.toEntity(calendarBlockDTO);
        CalendarBlock savedBlock = calendarBlockRepository.save(block);
        return calendarBlockMapper.toDTO(savedBlock);
    }

    @Override
    @Transactional
    public CalendarBlockDTO updateCalendarBlock(Long id, CalendarBlockDTO calendarBlockDTO) {
        if (!calendarBlockRepository.existsById(id)) {
            throw new RuntimeException("CalendarBlock not found with id: " + id);
        }
        calendarBlockDTO.setId(id);
        CalendarBlock block = calendarBlockMapper.toEntity(calendarBlockDTO);
        CalendarBlock updatedBlock = calendarBlockRepository.save(block);
        return calendarBlockMapper.toDTO(updatedBlock);
    }

    @Override
    @Transactional
    public void deleteCalendarBlock(Long id) {
        if (!calendarBlockRepository.existsById(id)) {
            throw new RuntimeException("CalendarBlock not found with id: " + id);
        }
        calendarBlockRepository.deleteById(id);
    }
}
