package ru.example.edu.dto;

import lombok.Data;

@Data
public class InviteCreateDTO {
    private long participant_id;
    private long meetup_id;
}
