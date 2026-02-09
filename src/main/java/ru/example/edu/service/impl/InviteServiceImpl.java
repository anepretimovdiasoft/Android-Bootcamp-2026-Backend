package ru.example.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.edu.dto.InviteCreateDTO;
import ru.example.edu.dto.InviteDTO;
import ru.example.edu.dto.InviteUpdateDTO;
import ru.example.edu.dto.InviteWithMeetupDTO;
import ru.example.edu.entity.Invite;
import ru.example.edu.entity.Meetup;
import ru.example.edu.entity.Person;
import ru.example.edu.exception.InviteNotFoundException;
import ru.example.edu.exception.MeetupNotFoundException;
import ru.example.edu.exception.PersonNotFoundException;
import ru.example.edu.repository.InviteRepository;
import ru.example.edu.repository.MeetupRepository;
import ru.example.edu.repository.PersonRepository;
import ru.example.edu.service.InviteService;
import ru.example.edu.service.MeetupService;
import ru.example.edu.util.InviteMapper;
import ru.example.edu.util.MeetupMapper;
import ru.example.edu.util.PersonMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {
    private final InviteRepository inviteRepository;
    private final PersonRepository personRepository;
    private final MeetupRepository meetupRepository;

    @Override
    public InviteDTO updateInvite(Long id, InviteUpdateDTO dto) {
        Invite invite = inviteRepository.findById(id).orElseThrow(() -> new InviteNotFoundException("Invite not found!"));

        invite.setAgree(dto.getAgree());
        return InviteMapper.convertToDto(inviteRepository.save(invite));
    }

    @Override
    public List<InviteWithMeetupDTO> getInvitesByParticipantId(Long id) {
        return inviteRepository.findByParticipantId(id).stream().map(InviteMapper::convertToDtoWithMeetup).collect(Collectors.toList());
    }

    @Override
    public InviteDTO createInvite(InviteCreateDTO dto) {
        Meetup meetup = meetupRepository.findById(dto.getMeetup_id()).orElseThrow(() -> new MeetupNotFoundException("Meetup with id " + dto.getMeetup_id()+ " not found!"));
        Person person = personRepository.findById(dto.getParticipant_id()).orElseThrow(() -> new PersonNotFoundException("Person with id " + dto.getParticipant_id() + " not found!"));

        Invite invite = new Invite();
        invite.setParticipant(person);
        invite.setMeetup(meetup);
        return InviteMapper.convertToDto(inviteRepository.save(invite));
    }
}
