package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingCreateDto;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingDto;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingMiniDto;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Meeting Controller", description = "Работа с встречами")
@RequestMapping("/api/v1/meeting")
public class MeetingController {

    @PostMapping("/create")
    @Operation(summary = "Создание новой встречи")
    public MeetingDto create(@RequestBody MeetingCreateDto meetingCreateDto) {
        return null;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о встрече")
    public MeetingDto getById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/schedule/day")
    @Operation(summary = "Получение расписания на день")
    public List<MeetingMiniDto> daySchedule(@RequestParam("day") LocalDate day) {
        return null;
    }

    @GetMapping("/schedule/week")
    @Operation(summary = "Получение расписания на неделю (принимает год и номер недели в году)")
    public List<MeetingMiniDto> weekSchedule(@RequestParam("year") int year, @RequestParam("week") int week) {
        return null;
    }

    @GetMapping("/schedule/month")
    @Operation(summary = "Получение расписания на месяц (принимает год и номер месяца в году)")
    public List<MeetingMiniDto> monthSchedule(@RequestParam("year") int year, @RequestParam("month") int month) {
        return null;
    }
}
