package ru.examle.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.examle.edu.dto.CalendarBlockDTO;
import ru.examle.edu.service.CalendarBlockService;

@RestController
@RequestMapping("/api/calendar-blocks")
@RequiredArgsConstructor
public class CalendarBlockController {

    private final CalendarBlockService calendarBlockService;

    @GetMapping
    public Page<CalendarBlockDTO> getAllCalendarBlocks(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "20") int size) {
        return calendarBlockService.getAllCalendarBlocks(PageRequest.of(page, size));
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
