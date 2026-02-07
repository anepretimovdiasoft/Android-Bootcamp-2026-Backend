package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.api.error.BadRequestException;
import ru.sicampus.bootcamp2026.api.dto.InvitationRequest;
import ru.sicampus.bootcamp2026.api.error.NotFoundException;
import ru.sicampus.bootcamp2026.domain.Invitation;
import ru.sicampus.bootcamp2026.domain.InvitationStatus;
import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.domain.Person;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.PersonRepository;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Invitation> list(Pageable pageable) {
        return invitationRepository.findAll(pageable);
    }

    public Page<Invitation> listForInvitee(Long inviteeId, InvitationStatus status, Pageable pageable) {
        return invitationRepository.findByInviteeIdAndStatus(inviteeId, status, pageable);
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

    public Invitation respond(Long invitationId, Long inviteeId, InvitationStatus status) {
        if (status == InvitationStatus.PENDING) {
            throw new BadRequestException("Status must be ACCEPTED or DECLINED");
        }

        Invitation invitation = get(invitationId);
        if (!invitation.getInvitee().getId().equals(inviteeId)) {
            throw new BadRequestException("Invitation does not belong to current user");
        }

        invitation.setStatus(status);
        invitation.setRespondedAt(OffsetDateTime.now(ZoneOffset.UTC));
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
