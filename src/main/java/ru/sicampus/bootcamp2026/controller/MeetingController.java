package ru.sicampus.bootcamp2026.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.*;
import ru.sicampus.bootcamp2026.service.MeetingService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Встречи и Расписание", description = "Методы для создания встреч и просмотра расписания")
public class MeetingController {
    private final MeetingService meetingService;

    @Operation(summary = "Создать встречу", description = "Создает новую встречу. Организатором становится пользователь из заголовка X-User-Id.")
    @PostMapping("/api/meetings")
    public ResponseEntity<ApiResponseDTO> create(
            @Parameter(in = ParameterIn.HEADER, description = "ID текущего пользователя", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId,
            @RequestBody MeetingCreateDTO req) {
        meetingService.createMeeting(userId, req);
        return ResponseEntity.ok(new ApiResponseDTO("Встреча создана"));
    }

    @Operation(summary = "Информация о встрече", description = "Возвращает подробную информацию о встрече по её ID")
    @GetMapping("/api/meetings/{id}")
    public ResponseEntity<MeetingInfoDTO> getInfo(@Parameter(description = "ID встречи") @PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getMeetingInfo(id));
    }

    @Operation(summary = "Получить расписание", description = "Возвращает список встреч пользователя за указанный период")
    @GetMapping("/api/schedule")
    public ResponseEntity<List<ScheduleEntryDTO>> getSchedule(
            @Parameter(in = ParameterIn.HEADER, description = "ID текущего пользователя", example = "1")
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId,
            @Parameter(description = "Начало периода", example = "2026-02-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Конец периода", example = "2026-02-28")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(meetingService.getSchedule(userId, startDate, endDate));
    }
}