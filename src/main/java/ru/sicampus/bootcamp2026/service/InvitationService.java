package ru.sicampus.bootcamp2026.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import ru.sicampus.bootcamp2026.api.dto.InvitationRequest;
import ru.sicampus.bootcamp2026.api.error.NotFoundException;
import ru.sicampus.bootcamp2026.domain.Invitation;
import ru.sicampus.bootcamp2026.domain.InvitationStatus;
import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.domain.Person;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final MeetingRepository meetingRepository;
    private final PersonRepository personRepository;

    public InvitationService(
        InvitationRepository invitationRepository,
        MeetingRepository meetingRepository,
        PersonRepository personRepository
    ) {
        this.invitationRepository = invitationRepository;
        this.meetingRepository = meetingRepository;
        this.personRepository = personRepository;
    }

    public List<Invitation> list() {
        return invitationRepository.findAll();
    }

    public Invitation get(Long id) {
        return invitationRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Invitation not found: " + id));
    }

    public Invitation create(InvitationRequest request) {
        Invitation invitation = new Invitation();
        apply(invitation, request);
        invitation.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return invitationRepository.save(invitation);
    }

    public Invitation update(Long id, InvitationRequest request) {
        Invitation invitation = get(id);
        apply(invitation, request);
        return invitationRepository.save(invitation);
    }

    public void delete(Long id) {
        if (!invitationRepository.existsById(id)) {
            throw new NotFoundException("Invitation not found: " + id);
        }
        invitationRepository.deleteById(id);
    }

    private void apply(Invitation invitation, InvitationRequest request) {
        Meeting meeting = meetingRepository.findById(request.meetingId())
            .orElseThrow(() -> new NotFoundException("Meeting not found: " + request.meetingId()));
        Person invitee = personRepository.findById(request.inviteeId())
            .orElseThrow(() -> new NotFoundException("Person not found: " + request.inviteeId()));
        invitation.setMeeting(meeting);
        invitation.setInvitee(invitee);
        invitation.setStatus(request.status() == null ? InvitationStatus.PENDING : request.status());
        invitation.setRespondedAt(request.respondedAt());
    }
}
