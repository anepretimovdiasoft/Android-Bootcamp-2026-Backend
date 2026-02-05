package ru.examle.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.examle.edu.entity.Invitation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationSimpleDto {
    private Long id;
    private MeetingSimpleDto meeting;
    private PersonSimpleDto person;
    private Invitation.InvitationStatus status;
}
