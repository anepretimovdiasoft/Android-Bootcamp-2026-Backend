package ru.sicampus.bootcamp2026.service.impl;

public enum ParticipantStatusEnum {
    WAITING_FOR_ANSWER(3L),
    ACCEPTED(1L),
    REJECTED(2L);

    private final Long statusId;

    ParticipantStatusEnum(Long statusId) {
        this.statusId = statusId;
    }

    public Long getCode() {
        return statusId;
    }
}
