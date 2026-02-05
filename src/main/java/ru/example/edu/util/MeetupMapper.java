package ru.example.edu.util;

import lombok.experimental.UtilityClass;
import ru.example.edu.dto.MeetupWithInvitesShortDTO;
import ru.example.edu.dto.MeetupWithInvitesDTO;
import ru.example.edu.dto.MeetupDTO;
import ru.example.edu.dto.MeetupShortDTO;
import ru.example.edu.entity.Meetup;

import java.util.stream.Collectors;

@UtilityClass
public class MeetupMapper {
    public MeetupDTO convertToDto(Meetup meetup) {
        MeetupDTO meetupDTO = new MeetupDTO();
        meetupDTO.setId(meetup.getId());
        meetupDTO.setDate(meetup.getDate());
        meetupDTO.setTime(meetup.getTime());
        meetupDTO.setPlanner(PersonMapper.convertToShortDtoWithInvites(meetup.getPlanner()));

        return meetupDTO;
    }

    public MeetupWithInvitesDTO convertToDtoWithInvites(Meetup meetup) {
        MeetupWithInvitesDTO meetupDTO = new MeetupWithInvitesDTO();
        meetupDTO.setId(meetup.getId());
        meetupDTO.setDate(meetup.getDate());
        meetupDTO.setTime(meetup.getTime());
        meetupDTO.setPlanner(PersonMapper.convertToShortDto(meetup.getPlanner()));
        meetupDTO.setInvites(meetup.getInvites().stream().map(InviteMapper::convertToDtoWithPerson).collect(Collectors.toList()));

        return meetupDTO;
    }

    public MeetupShortDTO convertToShortDto(Meetup meetup) {
        MeetupShortDTO meetupDTO = new MeetupShortDTO();
        meetupDTO.setId(meetup.getId());
        meetupDTO.setDate(meetup.getDate());
        meetupDTO.setTime(meetup.getTime());
        return meetupDTO;
    }

    public MeetupWithInvitesShortDTO convertToShortDtoWithInvites(Meetup meetup) {
        MeetupWithInvitesShortDTO meetupDTO = new MeetupWithInvitesShortDTO();
        meetupDTO.setId(meetup.getId());
        meetupDTO.setDate(meetup.getDate());
        meetupDTO.setTime(meetup.getTime());
        return meetupDTO;
    }
}
