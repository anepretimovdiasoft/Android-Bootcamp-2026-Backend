package ru.examle.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.examle.edu.dto.CalendarBlockDTO;

public interface CalendarBlockService {
    Page<CalendarBlockDTO> getAllCalendarBlocks(Pageable pageable);
    CalendarBlockDTO getCalendarBlockById(Long id);
    CalendarBlockDTO createCalendarBlock(CalendarBlockDTO calendarBlockDTO);
    CalendarBlockDTO updateCalendarBlock(Long id, CalendarBlockDTO calendarBlockDTO);
    void deleteCalendarBlock(Long id);
}
