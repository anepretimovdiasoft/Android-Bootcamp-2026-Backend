package ru.example.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.edu.dto.MeetupToCreateDTO;
import ru.example.edu.dto.MeetupWithInvitesDTO;
import ru.example.edu.dto.MeetupDTO;
import ru.example.edu.dto.MeetupShortDTO;
import ru.example.edu.entity.Meetup;
import ru.example.edu.entity.Person;
import ru.example.edu.exception.MeetupNotFoundException;
import ru.example.edu.exception.PersonNotFoundException;
import ru.example.edu.repository.MeetupRepository;
import ru.example.edu.repository.PersonRepository;
import ru.example.edu.service.MeetupService;
import ru.example.edu.util.MeetupMapper;
import ru.example.edu.util.PersonMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MeetupServiceImpl implements MeetupService  {
    private final MeetupRepository meetupRepository;
    private final PersonRepository personRepository;

    @Override
    public List<MeetupWithInvitesDTO> getAllMeetups() {
        return meetupRepository.findAll().stream().map(MeetupMapper::convertToDtoWithInvites).collect(Collectors.toList());
    }

    @Override
    public MeetupWithInvitesDTO getMeetupById(Long id) {
        return MeetupMapper.convertToDtoWithInvites(meetupRepository.findById(id).orElseThrow(() -> new MeetupNotFoundException("Meetup not found!")));
    }

    @Override
    public MeetupDTO createMeetup(MeetupToCreateDTO dto) {
        Optional<Person> optionalPerson = personRepository.findById(dto.getPlanner_id());
        if (optionalPerson.isEmpty()) {
            throw new PersonNotFoundException("Person not found!");
        }
        MeetupDTO meetup = new MeetupDTO();
        meetup.setDate(dto.getDate());
        meetup.setTime(dto.getTime());
        meetup.setPlanner(PersonMapper.convertToShortDtoWithInvites(optionalPerson.get()));
        return meetup;
    }

    @Override
    public MeetupShortDTO updateMeetup(Long id, MeetupShortDTO dto) {
        Meetup meetup = meetupRepository.findById(id).orElseThrow(() -> new MeetupNotFoundException("Meetup not found!"));

        meetup.setDate(dto.getDate());
        meetup.setTime(dto.getTime());
        return MeetupMapper.convertToShortDto(meetupRepository.save(meetup));
    }

    @Override
    public void deleteMeetup(Long id) {
        meetupRepository.findById(id).orElseThrow(() -> new MeetupNotFoundException("Meetup not found!"));
        meetupRepository.deleteById(id);
    }
}
