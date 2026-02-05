package com.teto.planner.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MeetingParticipantId implements Serializable {
    @Column(name = "meeting_id", nullable = false)
    private UUID meetingId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    public MeetingParticipantId() {}

    public MeetingParticipantId(UUID meetingId, UUID userId) {
        this.meetingId = meetingId;
        this.userId = userId;
    }

    public UUID getMeetingId() {
        return meetingId;
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingParticipantId that = (MeetingParticipantId) o;
        return Objects.equals(meetingId, that.meetingId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, userId);
    }
}
