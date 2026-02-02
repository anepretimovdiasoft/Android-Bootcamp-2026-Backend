package ru.sicampus.bootcamp2026.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class InvitationAnswerDTO {
    @NotNull(message = "Invitation ID cannot be null")
    private Long Id;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "^(ACCEPTED|DECLINED)$", message = "Status can only be ACCEPTED, PENDING, or DECLINED")
    private String status;
}
