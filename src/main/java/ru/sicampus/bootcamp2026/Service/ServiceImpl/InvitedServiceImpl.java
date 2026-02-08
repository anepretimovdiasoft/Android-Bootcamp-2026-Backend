package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.EmployeeNamesRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Infitations.GetUpdateInvitedRequest;
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
import ru.sicampus.bootcamp2026.Service.InvitedService;

import java.util.*;

@Service
public class InvitedServiceImpl implements InvitedService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private InvitedRepository invitedRepository;
    @Autowired
    private  InvitationsRepository invitationsRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Override
    public List<Invited> createdInviteds(List<EmployeeNamesRequest> stringList, Invitations invitations){
        List<Invited> inviteds=new ArrayList<>();
        String token=SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee1=employeeRepository.findByMail(token).orElseThrow(()->new EmployeeNotFound(""));
        for(EmployeeNamesRequest name:stringList){
            Employee employee=employeeRepository.findByMail(name.getName()).orElseThrow(()->new EmployeeNotFound(""));
            Invited invited=new Invited();
            invited.setEmployee(employee);
            invited.setInvitations(invitations);
            inviteds.add(invited);
            invitedRepository.save(invited);
        }
        return inviteds;
    }
    @Override
    public InvitedResponse getInvited(int page, int size) {

        String mail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Employee employee = employeeRepository.findByMail(mail)
                .orElseThrow(() -> new EmployeeNotFound(""));

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Invited> createdByMe =
                invitedRepository.findByInvitations_Employee(employee, pageable);

        Page<Invited> invitedMe =
                invitedRepository.findByEmployee(employee, pageable);

        List<Map<String, String>> result = new ArrayList<>();
        for (Invited invited : createdByMe.getContent()) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("Booking", invited.getInvitations().getBooking().getName());
            map.put("Employee",
                    invited.getInvitations().getEmployee().getName() + " " +
                            invited.getInvitations().getEmployee().getLast_name() + " " +
                            invited.getInvitations().getEmployee().getFather_name() + " " +
                            invited.getInvitations().getEmployee().getMail());
            map.put("start_time", invited.getInvitations().getBooking().getStart().toString());
            map.put("end_time", invited.getInvitations().getBooking().getEnd().toString());
            map.put("Approval", invited.getApproval().toString());
            result.add(map);
        }

        List<Map<String, String>> result1 = new ArrayList<>();
        for (Invited invited : invitedMe.getContent()) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("Booking", invited.getInvitations().getBooking().getName());
            map.put("Employee",
                    invited.getEmployee().getName() + " " +
                            invited.getEmployee().getLast_name() + " " +
                            invited.getEmployee().getFather_name() + " " +
                            invited.getEmployee().getMail());
            map.put("start_time", invited.getInvitations().getBooking().getStart().toString());
            map.put("end_time", invited.getInvitations().getBooking().getEnd().toString());
            map.put("Approval", invited.getApproval().toString());
            result1.add(map);
        }
        InvitedResponse response = new InvitedResponse();
        response.setResult(result);
        response.setResult1(result1);
        response.setTotalPages(createdByMe.getTotalPages());
        response.setTotalElements(createdByMe.getTotalElements());
        return response;
    }
    @Override
    public void updateInvited(GetUpdateInvitedRequest dto){
        String token=SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee=employeeRepository.findByMail(token).orElseThrow(()->new EmployeeNotFound(""));
        Booking booking=bookingRepository.findByName(dto.getName()).orElseThrow(()->new BookingNotFound(""));
        Invitations invitations=invitationsRepository.findByBooking(booking);
        List<Invited> inviteds=invitedRepository.findByInvitations(invitations);
        Invited invited = inviteds.stream()
                .filter(i -> i.getEmployee().equals(employee))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invited not found"));

        invited.setApproval(dto.getApproval());
        invitedRepository.save(invited);
    }
}
