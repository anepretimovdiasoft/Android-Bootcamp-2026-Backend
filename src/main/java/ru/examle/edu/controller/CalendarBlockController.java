package ru.examle.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.examle.edu.dto.CalendarBlockDTO;
import ru.examle.edu.service.CalendarBlockService;

import java.util.List;

@RestController
@RequestMapping("/api/calendar-blocks")
@RequiredArgsConstructor
public class CalendarBlockController {

    private final CalendarBlockService calendarBlockService;

    @GetMapping
    public List<CalendarBlockDTO> getAllCalendarBlocks() {
        return calendarBlockService.getAllCalendarBlocks();
    }

    @GetMapping("/{id}")
    public CalendarBlockDTO getCalendarBlockById(@PathVariable Long id) {
        return calendarBlockService.getCalendarBlockById(id);
    }

    @PostMapping
    public CalendarBlockDTO createCalendarBlock(@RequestBody CalendarBlockDTO calendarBlockDTO) {
        return calendarBlockService.createCalendarBlock(calendarBlockDTO);
    }

    @PutMapping("/{id}")
    public CalendarBlockDTO updateCalendarBlock(@PathVariable Long id, @RequestBody CalendarBlockDTO calendarBlockDTO) {
        return calendarBlockService.updateCalendarBlock(id, calendarBlockDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCalendarBlock(@PathVariable Long id) {
        calendarBlockService.deleteCalendarBlock(id);
    }
}
