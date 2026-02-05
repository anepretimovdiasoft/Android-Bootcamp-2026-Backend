package com.teto.planner.repository;

import java.util.UUID;

public interface BusyHoursProjection {
    UUID getUserId();
    Long getHours();
}
