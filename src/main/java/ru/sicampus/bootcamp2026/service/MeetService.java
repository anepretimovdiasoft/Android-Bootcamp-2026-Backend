package ru.sicampus.bootcamp2026.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meet;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.MeetConflictException;
import ru.sicampus.bootcamp2026.exception.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    public Page<Meet> getAllMeets(Pageable pageable) {
        return meetRepository.findAll(pageable);
    }

    public Meet getMeetById(Long id) {
        return meetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meet not found with id: " + id));
    }

    @Transactional
    public Meet createMeet(Meet meet, List<Long> inviteeIds) {
        if (meet.getMeetTime().getMinute() != 0 || meet.getMeetTime().getSecond() != 0) {
            throw new IllegalArgumentException("Meeting time must be at exact hour (e.g., 9:00, 10:00)");
        }


        checkTimeConflicts(meet.getOrganizer().getId(), meet.getMeetDate(), meet.getMeetTime());

        Meet savedMeet = meetRepository.save(meet);

        if (inviteeIds != null && !inviteeIds.isEmpty()) {
            for (Long userId : inviteeIds) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

                checkTimeConflicts(userId, meet.getMeetDate(), meet.getMeetTime());

                Invitation invitation = new Invitation();
                invitation.setMeet(savedMeet);
                invitation.setUser(user);
                invitation.setStatus("PENDING");

                invitationRepository.save(invitation);
            }
        }

        return savedMeet;
    }

    private void checkTimeConflicts(Long userId, LocalDate date, LocalTime time) {
        List<Meet> conflicts = meetRepository.findConflicts(userId, date, time);
        if (!conflicts.isEmpty()) {
            throw new MeetConflictException("User already has a meeting at this time");
        }
    }

    public Page<Meet> getUserMeets(Long userId, Pageable pageable) {
        return meetRepository.findByOrganizerId(userId, pageable);
    }

    public Page<Meet> getMeetsByDate(LocalDate date, Pageable pageable) {
        return meetRepository.findByMeetDate(date, pageable);
    }

    public Page<Meet> getMeetsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return meetRepository.findByMeetDateBetween(startDate, endDate, pageable);
    }

    public Meet updateMeet(Long id, Meet meetDetails) {
        Meet meet = getMeetById(id);
        meet.setTitle(meetDetails.getTitle());
        meet.setDescription(meetDetails.getDescription());
        meet.setMeetDate(meetDetails.getMeetDate());
        meet.setMeetTime(meetDetails.getMeetTime());
        return meetRepository.save(meet);
    }

    @Transactional
    public void deleteMeet(Long id) {
        Meet meet = getMeetById(id);
        List<Invitation> invitations = invitationRepository.findByMeetId(id);
        invitationRepository.deleteAll(invitations);
        meetRepository.delete(meet);
    }
}