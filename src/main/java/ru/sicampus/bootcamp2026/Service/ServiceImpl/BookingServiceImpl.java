package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Booking.GetBooingByDayRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Booking.GetBookingByWeekRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Booking.GetBookingUpdateRequest;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByDayResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByMonthResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingByWeekResponse;
import ru.sicampus.bootcamp2026.Dto.response.Booking.BookingUpdateResponse;
import ru.sicampus.bootcamp2026.Entity.Booking;
import ru.sicampus.bootcamp2026.Entity.Employee;
import ru.sicampus.bootcamp2026.Entity.Invitations;
import ru.sicampus.bootcamp2026.Entity.Invited;
import ru.sicampus.bootcamp2026.Excepations.EmployeeNotFound;
import ru.sicampus.bootcamp2026.Repository.BookingRepository;
import ru.sicampus.bootcamp2026.Repository.EmployeeRepository;
import ru.sicampus.bootcamp2026.Repository.InvitationsRepository;
import ru.sicampus.bootcamp2026.Repository.InvitedRepository;
import ru.sicampus.bootcamp2026.Service.BookingService;
import ru.sicampus.bootcamp2026.Service.TokenAuthService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private InvitationsRepository invitationsRepository;
    @Autowired
    private InvitedRepository invitedRepository;
    @Autowired
    private TokenAuthService tokenAuthService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public BookingByDayResponse getBookingByDay(GetBooingByDayRequest dto) {

        String mail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Employee employee = employeeRepository.findByMail(mail)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + mail));

        LocalDate date = dto.getDate();

        List<Map<String, Object>> ownBookings = new ArrayList<>();

        List<Booking> bookings = bookingRepository.findByEmployee(employee);

        for (Booking booking : bookings) {

            if (!booking.getStart().toLocalDate().equals(date)) continue;

            Map<String, Object> book = new LinkedHashMap<>();
            book.put("name", booking.getName());
            book.put("start", booking.getStart());
            book.put("end", booking.getEnd());
            book.put("admin", booking.getEmployee().getName() + "" + booking.getEmployee().getLast_name() + "" + booking.getEmployee().getFather_name());
            List<String> invitedNames =
                    invitedRepository
                            .findByInvitations(
                                    invitationsRepository.findByBooking(booking)
                            )
                            .stream()
                            .map(i -> i.getEmployee().getName())
                            .toList();
            book.put("invited", invitedNames);
            ownBookings.add(book);
        }
        List<Map<String, Object>> invitedBookings = new ArrayList<>();
        List<Invited> inviteds =
                invitedRepository.findByEmployee(employee)
                        .stream()
                        .filter(i -> Boolean.TRUE.equals(i.getApproval()))
                        .toList();
        for (Invited invited : inviteds) {
            Booking booking = invited.getInvitations().getBooking();
            if (!booking.getStart().toLocalDate().equals(date)) continue;
            if (booking.getEmployee().equals(employee)) continue;
            Map<String, Object> book = new LinkedHashMap<>();
            book.put("name", booking.getName());
            book.put("start", booking.getStart());
            book.put("end", booking.getEnd());
            book.put("admin", booking.getEmployee().getName() + "" + booking.getEmployee().getLast_name() + "" + booking.getEmployee().getFather_name());
            invitedBookings.add(book);
        }
        return new BookingByDayResponse(ownBookings, invitedBookings);
    }

    @Override
    public BookingByWeekResponse getBookingByWeek(GetBookingByWeekRequest dto) {
        String mail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Employee employee = employeeRepository.findByMail(mail)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + mail));
        LocalDate startDate = dto.getDate();
        LocalDate today = LocalDate.now();
        int days;
        if (startDate.isEqual(today)) {
            days = 7 - today.getDayOfWeek().getValue();
        } else {
            days = 6;
        }
        List<Booking> bookings = bookingRepository.findByEmployee(employee);
        Map<String, List<Map<String, Object>>> bookingByDay = new LinkedHashMap<>();
        List<Map<String, Object>> dailyBookings = null;
        List<Map<String, Object>> invitedBookings = null;
        dailyBookings = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            for (Booking booking : bookings) {
                if (!booking.getStart().toLocalDate().equals(currentDate)) continue;
                Map<String, Object> book = new LinkedHashMap<>();
                book.put("name", booking.getName());
                book.put("admin", booking.getEmployee().getName());
                book.put("start", booking.getStart().toLocalTime());
                book.put("end", booking.getEnd().toLocalTime());
                List<String> invitedNames =
                        invitedRepository
                                .findByInvitations(
                                        invitationsRepository.findByBooking(booking)
                                )
                                .stream()
                                .map(e -> e.getEmployee().getName())
                                .toList();
                book.put("invited", invitedNames);
                dailyBookings.add(book);
            }
            invitedBookings = new ArrayList<>();
            List<Invited> inviteds =
                    invitedRepository.findByEmployee(employee)
                            .stream()
                            .filter(e -> Boolean.TRUE.equals(e.getApproval()))
                            .toList();
            for (Invited invited : inviteds) {
                Booking booking = invited.getInvitations().getBooking();
                if (!booking.getStart().toLocalDate().equals(startDate)) continue;
                if (booking.getEmployee().equals(employee)) continue;
                Map<String, Object> book = new LinkedHashMap<>();
                book.put("name", booking.getName());
                book.put("start", booking.getStart());
                book.put("end", booking.getEnd());
                book.put("admin", booking.getEmployee().getName() + "" + booking.getEmployee().getLast_name() + "" + booking.getEmployee().getFather_name());
                invitedBookings.add(book);
            }
        }
        bookingByDay.put("ваши", dailyBookings);
        bookingByDay.put("вам", invitedBookings);
        return new BookingByWeekResponse(bookingByDay);
    }
    @Override
    public void updateBooking(GetBookingUpdateRequest dto){
        String token=SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee=employeeRepository.findByMail(token).orElseThrow();
        List<Booking> bookings=bookingRepository.findByEmployee(employee).stream().filter(b->b.getStart()==dto.getStart()||b.getEnd()==dto.getEnd()).toList();
        if(dto.getName()!=null){

        }
    }

}
