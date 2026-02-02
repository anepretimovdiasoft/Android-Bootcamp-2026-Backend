package ru.sicampus.bootcamp2026.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMeetingId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "meeting_id")
    private Long meetingId;
}