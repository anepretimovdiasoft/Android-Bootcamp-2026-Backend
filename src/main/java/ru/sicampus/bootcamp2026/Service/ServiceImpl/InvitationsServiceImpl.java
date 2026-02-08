package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.EmployeeNamesRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.GetInvitationsCreatedRequest;
import ru.sicampus.bootcamp2026.Entity.Booking;
import ru.sicampus.bootcamp2026.Entity.Employee;
import ru.sicampus.bootcamp2026.Entity.Invitations;
import ru.sicampus.bootcamp2026.Excepations.EmployeeNotFound;
import ru.sicampus.bootcamp2026.Repository.BookingRepository;
import ru.sicampus.bootcamp2026.Repository.EmployeeRepository;
import ru.sicampus.bootcamp2026.Repository.InvitationsRepository;
import ru.sicampus.bootcamp2026.Service.InvitationsService;

@Service
public class InvitationsServiceImpl implements InvitationsService {
    @Autowired
    private InvitationsRepository invitationsRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public void createdInvitations(GetInvitationsCreatedRequest dto){
        String token = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee=employeeRepository.findByMail(token).orElseThrow(()->new EmployeeNotFound(""));
        Booking booking=bookingRepository.findByName(dto.getName());
        if(!invitationsRepository.existsByBooking(booking)){
            Invitations invitation=new Invitations();
            invitation.setBooking(booking);
            invitation.setEmployee(employee);
        }

    }
}
