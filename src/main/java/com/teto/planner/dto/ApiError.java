package com.teto.planner.dto;

import java.util.Map;

public record ApiError(String code, String message, Map<String, Object> details) {
}
