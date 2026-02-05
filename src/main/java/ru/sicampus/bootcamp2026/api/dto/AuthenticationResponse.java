package ru.sicampus.bootcamp2026.api.dto;

import java.util.List;

public record AuthenticationResponse(
    String name,
    List<String> authorities,
    PersonResponse principal
) {
}

