package ru.sicampus.bootcamp2026.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public BookingByDayResponse getBookingByDay(GetBooingByDayRequest dto, int page, int size) {
        Employee employee = employeeRepository.findByMail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        Pageable pageable = PageRequest.of(page, size, Sort.by("start").ascending());
        Page<Booking> bookings = bookingRepository.findByEmployee(employee, pageable);
        LocalDate date = dto.getDate();
        List<Map<String,Object>> ownBookings = new ArrayList<>();
        for (Booking booking : bookings.getContent()) {
            if (!booking.getStart().toLocalDate().equals(date)) continue;
            Map<String,Object> book = new LinkedHashMap<>();
            book.put("name", booking.getName());
            book.put("start", booking.getStart());
            book.put("end", booking.getEnd());
            book.put("admin",
                    booking.getEmployee().getName() + " " +
                            booking.getEmployee().getLast_name() + " " +
                            booking.getEmployee().getFather_name()
            );
            ownBookings.add(book);
        }
        return new BookingByDayResponse(ownBookings, List.of());
    }
    @Override
    public BookingByWeekResponse getBookingByWeek(
            GetBookingByWeekRequest dto,
            int page,
            int size
    ) {
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
        Pageable pageable = PageRequest.of(page, size, Sort.by("start").ascending()
        );
        Page<Booking> bookingPage = bookingRepository.findByEmployee(employee, pageable);
        Map<String, List<Map<String, Object>>> bookingByDay = new LinkedHashMap<>();
        List<Map<String, Object>> ownBookings = new ArrayList<>();
        List<Map<String, Object>> invitedBookings = new ArrayList<>();
        for (Booking booking : bookingPage.getContent()) {
            LocalDate bookingDate = booking.getStart().toLocalDate();
            if (bookingDate.isBefore(startDate) ||
                    bookingDate.isAfter(startDate.plusDays(days))) continue;
            Map<String, Object> book = new LinkedHashMap<>();
            book.put("name", booking.getName());
            book.put("admin", booking.getEmployee().getName());
            book.put("start", booking.getStart().toLocalTime());
            book.put("end", booking.getEnd().toLocalTime());
            List<String> invitedNames = invitedRepository.findByInvitations(invitationsRepository.findByBooking(booking)
                            )
                            .stream()
                            .map(i -> i.getEmployee().getName())
                            .toList();

            book.put("invited", invitedNames);
            ownBookings.add(book);
        }
        List<Invited> inviteds =
                invitedRepository.findByEmployee(employee)
                        .stream()
                        .filter(i -> Boolean.TRUE.equals(i.getApproval()))
                        .toList();
        for (Invited invited : inviteds) {
            Booking booking = invited.getInvitations().getBooking();
            LocalDate bookingDate = booking.getStart().toLocalDate();
            if (bookingDate.isBefore(startDate) ||
                    bookingDate.isAfter(startDate.plusDays(days))) continue;
            if (booking.getEmployee().equals(employee)) continue;
            Map<String, Object> book = new LinkedHashMap<>();
            book.put("name", booking.getName());
            book.put("start", booking.getStart());
            book.put("end", booking.getEnd());
            book.put("admin",
                    booking.getEmployee().getName() + " " +
                            booking.getEmployee().getLast_name() + " " +
                            booking.getEmployee().getFather_name()
            );
            invitedBookings.add(book);
        }
        bookingByDay.put("ваши", ownBookings);
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
