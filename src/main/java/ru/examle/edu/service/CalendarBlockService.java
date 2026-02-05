package ru.examle.edu.service;

import ru.examle.edu.dto.CalendarBlockDTO;
import java.util.List;

public interface CalendarBlockService {
    List<CalendarBlockDTO> getAllCalendarBlocks();
    CalendarBlockDTO getCalendarBlockById(Long id);
    CalendarBlockDTO createCalendarBlock(CalendarBlockDTO calendarBlockDTO);
    CalendarBlockDTO updateCalendarBlock(Long id, CalendarBlockDTO calendarBlockDTO);
    void deleteCalendarBlock(Long id);
}
