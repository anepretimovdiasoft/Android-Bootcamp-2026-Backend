package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
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
    public InvitedResponse getInvited(){
        String token= SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee=employeeRepository.findByMail(token).orElseThrow(()->new EmployeeNotFound(""));
        List<Invitations> invitations=invitationsRepository.findByEmployee(employee);
        List<Map<String, String>> result=new ArrayList<>();
        List<Map<String,String>> result1=new ArrayList<>();
        for(Invitations invitations1:invitations){
            List<Invited> inviteds=invitedRepository.findByInvitations(invitations1);
            for(Invited invited:inviteds){
                Map<String, String> in=new LinkedHashMap<>();
                in.put("Booking",  invitations1.getBooking().getName());
                in.put("Employee", invitations1.getEmployee().getName()+" "+invitations1.getEmployee().getLast_name()+" "+invitations1.getEmployee().getFather_name()+" "+invitations1.getEmployee().getMail());
                in.put("start_time",invitations1.getBooking().getStart().toString());
                in.put("start_end",invitations1.getBooking().getEnd().toString());
                in.put("Approval",invited.getApproval().toString());
                result.add(in);
            }
        }
        List<Invited> inviteds=invitedRepository.findByEmployee(employee);
        for(Invited invited:inviteds){
            Map<String,String>  inv=new LinkedHashMap<>();
            inv.put("Booking",invited.getInvitations().getBooking().getName());
            inv.put("Employee",invited.getEmployee().getName()+ " "+invited.getEmployee().getLast_name()+" "+invited.getEmployee().getFather_name()+" "+invited.getEmployee().getMail());
            inv.put("start_time",invited.getInvitations().getBooking().getStart().toString());
            inv.put("end_time",invited.getInvitations().getBooking().getEnd().toString());
            inv.put("Approval",invited.getApproval().toString());
            result1.add(inv);
        }
        InvitedResponse response=new InvitedResponse();
        response.setResult(result);
        response.setResult1(result1);
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
