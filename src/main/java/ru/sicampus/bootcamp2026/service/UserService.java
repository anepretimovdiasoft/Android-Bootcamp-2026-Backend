package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingResponse;
import ru.sicampus.bootcamp2026.dto.UserDtos.CreateUserRequest;
import ru.sicampus.bootcamp2026.dto.UserDtos.InvitationDecisionRequest;
import ru.sicampus.bootcamp2026.dto.UserDtos.UpdateUserRequest;
import ru.sicampus.bootcamp2026.dto.UserDtos.UserResponse;

import java.util.List;

public interface UserService {

    Page<UserResponse> getAllUserPaginated(String search, Pageable pageable);

    List<UserResponse> list();

    List<MeetingResponse> listMeetingsByStatus(long userId, String status);

    InvitationDecisionRequest decideInvitation(long userId, long meetingId, InvitationDecisionRequest req);

    UserResponse get(long id);

    UserResponse getByLogin(String login);

    UserResponse create(CreateUserRequest req);

    UserResponse update(long id, UpdateUserRequest req);

    void delete(long id);
}