package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sicampus.bootcamp2026.dto.JobTitleDTO;
import ru.sicampus.bootcamp2026.service.JobTitleService;

import java.util.List;

@RestController
@RequestMapping("/api/job-titles")
@RequiredArgsConstructor
@Tag(name = "Должности", description = "Методы для работы со списком должностей")
public class JobTitleController {

    private final JobTitleService jobTitleService;

    @Operation(summary = "Получить все должности")
    @GetMapping
    public ResponseEntity<List<JobTitleDTO>> getAllJobTitles() {
        return ResponseEntity.ok(jobTitleService.getAllJobTitles());
    }
}
