package ru.example.edu.service;

import ru.example.edu.dto.MeetupToCreateDTO;
import ru.example.edu.dto.MeetupWithInvitesDTO;
import ru.example.edu.dto.MeetupDTO;
import ru.example.edu.dto.MeetupShortDTO;

import java.util.List;

public interface MeetupService {
    List<MeetupWithInvitesDTO> getAllMeetups();

    MeetupWithInvitesDTO getMeetupById(Long id);

    MeetupDTO createMeetup(MeetupToCreateDTO dto);

    MeetupShortDTO updateMeetup(Long id, MeetupShortDTO dto);

    void deleteMeetup(Long id);

    List<MeetupWithInvitesDTO> getAllPersonsMeetups(Long id);
}
