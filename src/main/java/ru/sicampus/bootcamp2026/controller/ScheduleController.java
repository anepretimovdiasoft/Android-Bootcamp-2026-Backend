package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingDto;
import ru.sicampus.bootcamp2026.service.ScheduleService;
import ru.sicampus.bootcamp2026.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;

    /**
     * Получить расписание на конкретную дату.
     * Пример: GET /api/schedule/v1/me/day?date=2026-02-10
     */
    @GetMapping("/v1/me/day")
    public ResponseEntity<List<MeetingDto>> getDaySchedule(
            Authentication authentication,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        String login = authentication.getName();
        Long id = userService.getUserByLogin(login).getId();
        List<MeetingDto> schedule = scheduleService.getDaySchedule(id, date);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Получить расписание на текущую неделю (с понедельника).
     * Пример: GET /api/schedule/v1/me/week
     */
    @GetMapping("/v1/me/week")
    public ResponseEntity<List<MeetingDto>> getWeekSchedule(
            Authentication authentication
    ) {
        String login = authentication.getName();
        Long id = userService.getUserByLogin(login).getId();
        List<MeetingDto> schedule = scheduleService.getWeekSchedule(id);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Получить расписание на две недели (с понедельника текущей недели).
     * Пример: GET /api/schedule/v1/me/two-weeks
     */
    @GetMapping("/v1/me/two-weeks")
    public ResponseEntity<List<MeetingDto>> getTwoWeeksSchedule(
            Authentication authentication
    ) {
        String login = authentication.getName();
        Long id = userService.getUserByLogin(login).getId();
        List<MeetingDto> schedule = scheduleService.getTwoWeeksSchedule(id);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Получить расписание на текущий месяц.
     * Пример: GET /api/schedule/v1/me/month
     */
    @GetMapping("/v1/me/month")
    public ResponseEntity<List<MeetingDto>> getMonthSchedule(
            Authentication authentication
    ) {
        String login = authentication.getName();
        Long id = userService.getUserByLogin(login).getId();
        List<MeetingDto> schedule = scheduleService.getMonthSchedule(id);
        return ResponseEntity.ok(schedule);
    }

}
