package com.teto.planner.controller;

import com.teto.planner.dto.IntersectionResponse;
import com.teto.planner.entity.UserEntity;
import com.teto.planner.service.CurrentUserService;
import com.teto.planner.service.SchedulingService;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
public class SchedulingController {
    private final SchedulingService schedulingService;
    private final CurrentUserService currentUserService;

    public SchedulingController(SchedulingService schedulingService, CurrentUserService currentUserService) {
        this.schedulingService = schedulingService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/intersection")
    public IntersectionResponse getIntersection(
            @RequestParam("meetingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate meetingDate,
            @RequestParam("userIds") List<UUID> userIds
    ) {
        UserEntity organizer = currentUserService.getCurrentUser();
        return schedulingService.getIntersection(organizer, meetingDate, userIds);
    }
}
