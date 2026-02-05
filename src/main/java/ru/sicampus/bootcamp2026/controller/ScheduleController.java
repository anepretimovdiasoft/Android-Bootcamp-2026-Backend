package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingDto;
import ru.sicampus.bootcamp2026.service.ScheduleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Получить расписание на конкретную дату.
     * Пример: GET /api/schedule/v1/1/day?date=2026-02-10
     */
    @GetMapping("/v1/{id}/day")
    public ResponseEntity<List<MeetingDto>> getDaySchedule(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<MeetingDto> schedule = scheduleService.getDaySchedule(id, date);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Получить расписание на текущую неделю (с понедельника).
     * Пример: GET /api/schedule/v1/1/week
     */
    @GetMapping("/v1/{id}/week")
    public ResponseEntity<List<MeetingDto>> getWeekSchedule(
            @PathVariable Long id
    ) {
        List<MeetingDto> schedule = scheduleService.getWeekSchedule(id);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Получить расписание на две недели (с понедельника текущей недели).
     * Пример: GET /api/schedule/v1/1/two-weeks
     */
    @GetMapping("/v1/{id}/two-weeks")
    public ResponseEntity<List<MeetingDto>> getTwoWeeksSchedule(
            @PathVariable Long id
    ) {
        List<MeetingDto> schedule = scheduleService.getTwoWeeksSchedule(id);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Получить расписание на текущий месяц.
     * Пример: GET /api/schedule/v1/1/month
     */
    @GetMapping("/v1/{id}/month")
    public ResponseEntity<List<MeetingDto>> getMonthSchedule(
            @PathVariable Long id
    ) {
        List<MeetingDto> schedule = scheduleService.getMonthSchedule(id);
        return ResponseEntity.ok(schedule);
    }

}
