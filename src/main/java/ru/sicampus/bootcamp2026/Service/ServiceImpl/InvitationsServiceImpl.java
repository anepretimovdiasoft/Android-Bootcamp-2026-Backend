package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.GetInvitationsCreatedRequest;
import ru.sicampus.bootcamp2026.Dto.response.Invited.InvitedResponse;
import ru.sicampus.bootcamp2026.Entity.Booking;
import ru.sicampus.bootcamp2026.Entity.Employee;
import ru.sicampus.bootcamp2026.Entity.Invitations;
import ru.sicampus.bootcamp2026.Entity.Invited;
import ru.sicampus.bootcamp2026.Excepations.BookingNotFound;
import ru.sicampus.bootcamp2026.Excepations.EmployeeNotFound;
import ru.sicampus.bootcamp2026.Repository.BookingRepository;
import ru.sicampus.bootcamp2026.Repository.EmployeeRepository;
import ru.sicampus.bootcamp2026.Repository.InvitationsRepository;
import ru.sicampus.bootcamp2026.Repository.InvitedRepository;
import ru.sicampus.bootcamp2026.Service.InvitationsService;
import ru.sicampus.bootcamp2026.Service.InvitedService;

import java.util.*;

@Service
public class InvitationsServiceImpl implements InvitationsService {
    @Autowired
    private InvitationsRepository invitationsRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private InvitedService invitedService;
    @Autowired
    private InvitedRepository invitedRepository;
    @Override
    public void createdInvitations(GetInvitationsCreatedRequest dto){
        String token = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee=employeeRepository.findByMail(token).orElseThrow(()->new EmployeeNotFound(""));
        Booking booking=bookingRepository.findByName(dto.getName()).orElseThrow(()->new BookingNotFound(""));
        Invitations invitation=new Invitations();
        if(!invitationsRepository.existsByBooking(booking)){
            invitation.setBooking(booking);
            invitation.setEmployee(employee);
        }
        invitationsRepository.save(invitation);
        invitedService.createdInviteds(dto.getInviteds(),invitation);
    }
}
