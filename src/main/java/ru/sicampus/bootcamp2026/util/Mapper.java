package ru.sicampus.bootcamp2026.util;

import org.springframework.security.core.GrantedAuthority;
import ru.sicampus.bootcamp2026.dto.response.UserResponse;
import ru.sicampus.bootcamp2026.dto.response.MeetResponse;
import ru.sicampus.bootcamp2026.dto.response.InvitationResponse;
import ru.sicampus.bootcamp2026.dto.response.PageResponse;
import ru.sicampus.bootcamp2026.entity.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    // User -> UserResponse
    public static UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setPhotoUrl(user.getPhotoUrl());
        response.setPosition(user.getPosition());

        // Роли
        if (user.getAuthorities() != null) {
            List<String> roles = new ArrayList<>();
            for (GrantedAuthority authority : user.getAuthorities()) {
                roles.add(authority.getAuthority());
            }
            response.setRoles(roles);
        }

        return response;
    }

    // Meet -> MeetResponse
    public static MeetResponse toMeetResponse(Meet meet) {
        MeetResponse response = new MeetResponse();
        response.setId(meet.getId());
        response.setTitle(meet.getTitle());
        response.setDescription(meet.getDescription());
        response.setMeetDate(meet.getMeetDate());
        response.setMeetTime(meet.getMeetTime());

        if (meet.getOrganizer() != null) {
            response.setOrganizer(toUserResponse(meet.getOrganizer()));
        }

        return response;
    }

    // Invitation -> InvitationResponse
    public static InvitationResponse toInvitationResponse(Invitation invitation) {
        InvitationResponse response = new InvitationResponse();
        response.setId(invitation.getId());
        response.setStatus(invitation.getStatus());
        response.setRespondedAt(invitation.getRespondedAt());

        if (invitation.getMeet() != null) {
            response.setMeet(toMeetResponse(invitation.getMeet()));
        }

        if (invitation.getUser() != null) {
            response.setUser(toUserResponse(invitation.getUser()));
        }

        return response;
    }

    // Page<User> -> PageResponse<UserResponse>
    public static PageResponse<UserResponse> toUserPageResponse(Page<User> page) {
        List<UserResponse> content = new ArrayList<>();
        for (User user : page.getContent()) {
            content.add(toUserResponse(user));
        }

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    // Page<Meet> -> PageResponse<MeetResponse>
    public static PageResponse<MeetResponse> toMeetPageResponse(Page<Meet> page) {
        List<MeetResponse> content = new ArrayList<>();
        for (Meet meet : page.getContent()) {
            content.add(toMeetResponse(meet));
        }

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    // Page<Invitation> -> PageResponse<InvitationResponse>
    public static PageResponse<InvitationResponse> toInvitationPageResponse(Page<Invitation> page) {
        List<InvitationResponse> content = new ArrayList<>();
        for (Invitation invitation : page.getContent()) {
            content.add(toInvitationResponse(invitation));
        }

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}