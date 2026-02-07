package ru.sicampus.bootcamp2026.web.mappers;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingDto;
import ru.sicampus.bootcamp2026.web.dto.invitation.UserMiniInvitationDto;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingMiniDto;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MeetingMapper {

    public MeetingDto toDto(Meeting meeting, List<UserMiniInvitationDto> users) {
        return MeetingDto.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .address(meeting.getAddress())
                .description(meeting.getDescription())
                .date(meeting.getDate())
                .timeStart(meeting.getTimeStart())
                .timeEnd(meeting.getTimeEnd())
                .organizer(UserMapper.toMiniDto(meeting.getOrganizer()))
                .users(users)
                .createAt(meeting.getCreatedAt())
                .build();
    }

    public MeetingMiniDto toMiniDto(Meeting meeting) {
        return MeetingMiniDto.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .description(meeting.getDescription())
                .address(meeting.getAddress())
                .date(meeting.getDate())
                .timeStart(meeting.getTimeStart())
                .timeEnd(meeting.getTimeEnd())
                .organizer(UserMapper.toMiniDto(meeting.getOrganizer()))
                .build();
    }

    public List<MeetingMiniDto> toMiniDtoList(List<Meeting> meetings) {
        return meetings.stream()
                .map(MeetingMapper::toMiniDto)
                .collect(Collectors.toList());
    }

}
