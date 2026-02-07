package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingCreateDto;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingDto;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingMiniDto;
import ru.sicampus.bootcamp2026.web.mappers.MeetingMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Meeting Controller", description = "Работа с встречами")
@RequestMapping("/api/v1/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping("/create")
    @Operation(summary = "Создание новой встречи")
    public MeetingDto create(@Validated @RequestBody MeetingCreateDto meetingCreateDto) {
        return meetingService.create(meetingCreateDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о встрече")
    public MeetingDto getById(@PathVariable Long id) {
        return meetingService.getById(id);
    }

    @GetMapping("/schedule/day")
    @Operation(summary = "Получение расписания на день")
    public List<MeetingMiniDto> daySchedule(@RequestParam("day") LocalDate day) {
        return MeetingMapper.toMiniDtoList(meetingService.daySchedule(day));
    }

    @GetMapping("/schedule/week")
    @Operation(summary = "Получение расписания на неделю (принимает год и номер недели в году)")
    public Map<LocalDate, List<MeetingMiniDto>> weekSchedule(@RequestParam("year") int year, @RequestParam("week") int week) {
        return meetingService.weekSchedule(year, week);
    }

    @GetMapping("/schedule/month")
    @Operation(summary = "Получение расписания на месяц (принимает год и номер месяца в году)")
    public Map<LocalDate, List<MeetingMiniDto>> monthSchedule(@RequestParam("year") int year, @RequestParam("month") int month) {
        return meetingService.monthSchedule(year, month);
    }
}
