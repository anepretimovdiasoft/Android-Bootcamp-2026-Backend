package ru.sicampus.bootcamp2026.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class InvitationAnswerDTO {
    @NotBlank
    private Long Id;

    @NotBlank
    @Pattern(regexp = "^(ACCEPTED|PENDING|DECLINED)$")
    private String status;
}
