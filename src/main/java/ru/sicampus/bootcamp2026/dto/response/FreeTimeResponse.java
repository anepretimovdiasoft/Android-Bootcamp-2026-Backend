package ru.sicampus.bootcamp2026.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeTimeResponse {

    private List<FreeTimeSlot> startEndTime;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FreeTimeSlot {
        private Instant startTime;
        private Instant endTime;
    }
}
